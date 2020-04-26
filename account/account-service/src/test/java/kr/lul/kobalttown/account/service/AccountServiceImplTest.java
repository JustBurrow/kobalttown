package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.data.repository.EnableCodeRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.service.configuration.EnableCodeConfig;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountSystemParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

import static java.util.UUID.randomUUID;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
import static kr.lul.kobalttown.account.domain.EnableCode.TOKEN_REGEX;
import static kr.lul.kobalttown.account.domain.EnableCode.TTL_DEFAULT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountServiceTestConfiguration.class)
@Transactional
public class AccountServiceImplTest {
  private static final Logger log = getLogger(AccountServiceImplTest.class);

  @Autowired
  private EnableCodeConfig enableCode;

  @Autowired
  private AccountService service;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private EnableCodeRepository enableCodeRepository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    log.info("SETUP - enableCode={}", this.enableCode);

    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    final ReadAccountSystemParams params = new ReadAccountSystemParams(new Context(), Long.MAX_VALUE, this.before);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Account account = this.service.read(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
  }

  @Test
  public void test_read() throws Exception {
    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final Account expected = this.service.create(
        new CreateAccountParams(randomUUID(), nickname, email, userKey, "password", this.before));
    log.info("GIVEN - expected={}", expected);

    this.entityManager.flush();
    this.entityManager.clear();

    final Context context = new Context();
    final Instant timestamp = this.timeProvider.now();

    // WHEN
    final Account actual = this.service.read(new ReadAccountSystemParams(context, expected.getId(), timestamp));
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getId, Account::getNickname, Account::isEnabled, Account::getCreatedAt, Account::getUpdatedAt)
        .containsSequence(expected.getId(), nickname, !this.enableCode.isEnable(), this.before, this.before);
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  @Rollback(false)
  public void test_create() throws Exception {
    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final CreateAccountParams params = new CreateAccountParams(randomUUID(), nickname, email, userKey, "password", this.before);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Account account = this.service.create(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNotNull()
        .extracting(Account::getNickname, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(nickname, !this.enableCode.isEnable(), this.before, this.before);
    assertThat(account.getId())
        .isPositive();

    Credential credential = this.credentialRepository.findByPublicKey(email);
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey, Creatable::getCreatedAt)
        .containsSequence(account, email, this.before);
    assertThat(credential.getId())
        .isPositive();
    assertThat(credential.getSecretHash())
        .isNotEmpty();

    credential = this.credentialRepository.findByPublicKey(userKey);
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey, Creatable::getCreatedAt)
        .containsSequence(account, userKey, this.before);
    assertThat(credential.getId())
        .isPositive();
    assertThat(credential.getSecretHash())
        .isNotEmpty();

    if (this.enableCode.isEnable()) {
      // TODO mockup으로 활성화 비활성화 모두 테스트하기.
      final List<EnableCode> codes = this.enableCodeRepository.findAllByAccount((AccountEntity) account);

      assertThat(codes)
          .isNotNull()
          .hasSize(1)
          .doesNotContainNull();

      final EnableCode code = codes.get(0);
      log.info("THEN - code={}", code);

      assertThat(code)
          .extracting(EnableCode::getExpireAt, EnableCode::isUsed, EnableCode::getStatusAt, EnableCode::isExpired)
          .containsSequence(this.before.plus(TTL_DEFAULT), false, this.before, false);
      assertThat(code.getId())
          .isPositive();
      assertThat(code.getToken())
          .isNotNull()
          .matches(TOKEN_REGEX);
    }
  }

  @Test
  public void test_create_when_enabe_code_disabled() throws Exception {
    // TODO 설정을 mockup으로 변경.
    if (this.enableCode.isEnable()) {
      log.info("enable code is enabled.");
      return;
    }

    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final CreateAccountParams params = new CreateAccountParams(randomUUID(), nickname, email, userKey, "password", this.before);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Account account = this.service.create(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNotNull()
        .extracting(Account::getNickname, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(nickname, true, this.before, this.before);
    assertThat(account.getId())
        .isPositive();
  }
}
