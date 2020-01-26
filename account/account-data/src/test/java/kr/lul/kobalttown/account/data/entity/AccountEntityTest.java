package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.AccountFactoryImpl;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.ATTR_NICKNAME;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class AccountEntityTest {
  private static final Logger log = getLogger(AccountEntityTest.class);

  private AccountFactory factory;
  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.factory = new AccountFactoryImpl();
    this.before = Instant.now();
  }

  @Test
  public void test_new_with_null_nickname_and_disabled_and_null_createdAt() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> new AccountEntity(null, false, null))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("nickname is null.")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, null);
  }

  @Test
  public void test_new_with_empty_nickname_and_disabled_and_null_createdAt() throws Exception {
    assertThatThrownBy(() -> new AccountEntity("", false, null))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, "");
  }

  @Test
  public void test_new_with_nickname_and_disabled_and_null_createdAt() throws Exception {
    assertThatThrownBy(() -> new AccountEntity(random(current().nextInt(1, NICKNAME_MAX_LENGTH + 1)), false, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_new_with_nickname_and_disabled_and_createdAt() throws Exception {
    // GIVEN
    final String nickname = nickname();
    final Instant createdAt = Instant.now();
    log.info("GIVEN - nickname={}, createdAt={}", nickname, createdAt);

    // WHEN
    final AccountEntity account = new AccountEntity(nickname, false, createdAt);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .extracting(AccountEntity::getId, AccountEntity::getNickname, AccountEntity::isEnabled,
            SavableEntity::getCreatedAt, SavableEntity::getUpdatedAt)
        .containsSequence(0L, nickname, false, createdAt, createdAt);
  }

  @Test
  public void test_enable_with_null() throws Exception {
    // GIVEN
    final Account account = this.factory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);

    // WHEN & THEN
    assertThatThrownBy(() -> account.enable(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("enableAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_enable_before_createdAt() throws Exception {
    // GIVEN
    final Account account = this.factory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);

    final Instant enableAt = this.before.minusNanos(1L);
    log.info("GIVEN - enableAt={}", enableAt);

    // WHEN & THEN
    assertThatThrownBy(() -> account.enable(enableAt))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early enable at " + enableAt)
        .hasNoCause();
  }
}
