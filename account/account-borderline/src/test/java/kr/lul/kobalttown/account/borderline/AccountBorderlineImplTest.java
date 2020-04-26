package kr.lul.kobalttown.account.borderline;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.data.dao.EnableCodeDao;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.service.configuration.EnableCodeConfig;
import kr.lul.support.spring.common.context.ContextService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

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
@SpringBootTest(classes = AccountBorderlineTestConfiguration.class)
public class AccountBorderlineImplTest {
  private static final Logger log = getLogger(AccountBorderlineImplTest.class);

  @Autowired
  private EnableCodeConfig enableCode;

  @Autowired
  private AccountBorderline borderline;
  @Autowired
  private EnableCodeDao enableCodeDao;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private ContextService contextService;

  private Context context;
  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    this.context = this.contextService.issue();
    log.info("SETUP - context={}", this.context);
    this.before = this.timeProvider.zonedDateTime();
    log.info("SETUP - before={}", this.before);
  }

  @After
  public void tearDown() throws Exception {
    this.contextService.clear();
  }

  @Test
  public void test_read_with_null() throws Exception {
    assertThatThrownBy(() -> this.borderline.read(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    final long id = Long.MAX_VALUE;
    log.info("GIVEN - id={}", id);
    final ReadAccountCmd cmd = new ReadAccountCmd(new Context(), id, this.timeProvider.now());
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final AccountDetailDto account = this.borderline.read(cmd);
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
    final Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - nickname={}, email={}, userKey={}, createdAt={}", nickname, email, userKey, createdAt);

    final AccountDetailDto expected = this.borderline.create(
        new CreateAccountCmd(this.context, nickname, email, userKey, "password", createdAt));
    log.info("GIVEN - expected={}", expected);

    final ReadAccountCmd cmd = new ReadAccountCmd(this.context, expected.getId(), this.timeProvider.now());
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final AccountDetailDto actual = this.borderline.read(cmd);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(AccountDetailDto::getId, AccountDetailDto::getNickname, AccountDetailDto::isEnabled,
            AccountDetailDto::getCreatedAt, AccountDetailDto::getUpdatedAt)
        .containsSequence(expected.getId(), nickname, !this.enableCode.isEnable(),
            this.timeProvider.zonedDateTime(createdAt), this.timeProvider.zonedDateTime(createdAt));
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.borderline.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final String nickname = nickname();
    final String email = email();
    final String userKey = userKey();
    final String password = "password";
    final Instant createdAt = this.before.toInstant();
    final CreateAccountCmd cmd = new CreateAccountCmd(new Context(), nickname, email, userKey, password, createdAt);
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final AccountDetailDto dto = this.borderline.create(cmd);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AccountDetailDto::getNickname, AccountDetailDto::isEnabled)
        .containsSequence(nickname, !this.enableCode.isEnable());
    assertThat(dto.getId())
        .isPositive();
    assertThat(dto.getCreatedAt())
        .isNotNull()
        .isEqualTo(dto.getUpdatedAt())
        .isEqualTo(this.before);

    Credential credential = this.credentialRepository.findByPublicKey(email);
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getPublicKey)
        .isEqualTo(email);

    credential = this.credentialRepository.findByPublicKey(userKey);
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getPublicKey)
        .isEqualTo(userKey);

    final List<EnableCode> codes = this.enableCodeDao.list(new Context(), email);
    log.info("THEN - validationCodes={}", codes);
    if (this.enableCode.isEnable()) {
      assertThat(codes)
          .isNotNull();
      assertThat(codes.get(0))
          .isNotNull()
          .extracting(EnableCode::isUsed, EnableCode::getStatusAt, EnableCode::isExpired)
          .containsSequence(false, createdAt, false);
      assertThat(codes.get(0).getAccount())
          .isNotNull()
          .extracting(Account::getId, Account::isEnabled)
          .containsSequence(dto.getId(), false);
    }
  }
}
