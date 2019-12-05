package kr.lul.kobalttown.account.borderline;

import kr.lul.common.data.UuidContext;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

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
  private AccountBorderline borderline;
  @Autowired
  private TimeProvider timeProvider;

  private ZonedDateTime before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.borderline).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.zonedDateTime();
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
    long id = Long.MAX_VALUE;
    log.info("GIVEN - id={}", id);
    ReadAccountCmd cmd = new ReadAccountCmd(new UuidContext(UUID.randomUUID()), id);
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    AccountDetailDto account = this.borderline.read(cmd);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
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
    String nickname = "nickname #" + current().nextInt(Integer.MAX_VALUE);
    String email = "just.burrow." + current().nextInt(Integer.MAX_VALUE) + "@lul.kr";
    String password = "password";
    CreateAccountCmd cmd = new CreateAccountCmd(new UuidContext(), nickname, email, password, Instant.now());
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    AccountDetailDto dto = this.borderline.create(cmd);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AccountDetailDto::getNickname, AccountDetailDto::isEnabled)
        .containsSequence(nickname, false);
    assertThat(dto.getId())
        .isPositive();
    assertThat(dto.getCreatedAt())
        .isNotNull()
        .isEqualTo(dto.getUpdatedAt())
        .isAfter(this.before);
  }
}
