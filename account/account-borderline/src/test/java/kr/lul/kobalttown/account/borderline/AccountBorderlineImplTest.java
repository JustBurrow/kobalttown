package kr.lul.kobalttown.account.borderline;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.service.configuration.ActivateCodeConfiguration;
import kr.lul.support.spring.web.context.ContextService;
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

import static java.util.concurrent.ThreadLocalRandom.current;
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
  private ActivateCodeConfiguration activateCode;

  @Autowired
  private AccountBorderline borderline;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private ContextService contextService;

  private Context context;
  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.borderline).isNotNull();
    assertThat(this.contextService).isNotNull();
    assertThat(this.activateCode).isNotNull();
    log.info("SETUP - activateCode={}", this.activateCode);
    assertThat(this.timeProvider).isNotNull();

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
    final String nickname = "nickname #" + current().nextInt(Integer.MAX_VALUE);
    final String email = "just.burrow+" + current().nextInt(Integer.MAX_VALUE) + "@lul.kr";
    final Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - nickname={}, email={}, createdAt={}", nickname, email, createdAt);

    final AccountDetailDto expected = this.borderline
        .create(new CreateAccountCmd(this.context, nickname, email, "password", createdAt));
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
        .containsSequence(expected.getId(), nickname, !this.activateCode.isEnable(),
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
    final String nickname = "nickname #" + current().nextInt(Integer.MAX_VALUE);
    final String email = "just.burrow." + current().nextInt(Integer.MAX_VALUE) + "@lul.kr";
    final String password = "password";
    final CreateAccountCmd cmd = new CreateAccountCmd(new Context(), nickname, email, password, Instant.now());
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    final AccountDetailDto dto = this.borderline.create(cmd);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AccountDetailDto::getNickname, AccountDetailDto::isEnabled)
        .containsSequence(nickname, !this.activateCode.isEnable());
    assertThat(dto.getId())
        .isPositive();
    assertThat(dto.getCreatedAt())
        .isNotNull()
        .isEqualTo(dto.getUpdatedAt())
        .isAfter(this.before);
  }
}
