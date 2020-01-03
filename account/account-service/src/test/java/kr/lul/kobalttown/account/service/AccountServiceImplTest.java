package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.service.configuration.ActivateCodeConfiguration;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
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
  private ActivateCodeConfiguration activateCode;

  @Autowired
  private AccountService service;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private EntityManager entityManager;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.service).isNotNull();
    assertThat(this.entityManager).isNotNull();
    assertThat(this.activateCode).isNotNull();
    log.info("SETUP - activateCode={}", this.activateCode);
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    final ReadAccountParams params = new ReadAccountParams(new Context(), Long.MAX_VALUE, this.before);
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
        new CreateAccountParams(new Context(), nickname, email, userKey, "password", this.before));
    log.info("GIVEN - expected={}", expected);

    this.entityManager.flush();
    this.entityManager.clear();

    final Context context = new Context();
    final Instant timestamp = this.timeProvider.now();

    // WHEN
    final Account actual = this.service.read(new ReadAccountParams(context, expected.getId(), timestamp));
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getId, Account::getNickname, Account::isEnabled,
            Account::getCreatedAt, Account::getUpdatedAt)
        .containsSequence(expected.getId(), nickname, !this.activateCode.isEnable(), this.before, this.before);
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final CreateAccountParams params = new CreateAccountParams(new Context(), nickname, email, userKey,
        "password", this.before);
    log.info("GIVEN - params={}", params);

    // WHEN
    final Account account = this.service.create(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNotNull()
        .extracting(Account::getNickname, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(nickname, !this.activateCode.isEnable(), this.before, this.before);
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
  }

  @Test
  public void test_create_when_activate_code_disabled() throws Exception {
    if (this.activateCode.isEnable()) {
      log.info("activate code is enabled.");
      return;
    }

    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final CreateAccountParams params = new CreateAccountParams(new Context(), nickname, email, userKey,
        "password", this.before);
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
