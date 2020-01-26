package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.EnableCode.*;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.ttl;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class EnableCodeTest {
  private static final Logger log = getLogger(EnableCodeTest.class);

  @Test
  public void test_ACCOUNT_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_ACCOUNT_VALIDATOR_with_not_persisted() throws Exception {
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(new Account() {
      @Override
      public long getId() {
        return 0L;
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant enableAt) {

      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    }))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("illegal account id")
        .hasNoCause();
  }

  @Test
  public void test_ACCOUNT_VALIDATOR_with_enabled() throws Exception {
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(new Account() {
      @Override
      public long getId() {
        return current().nextLong(1L, Long.MAX_VALUE);
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }

      @Override
      public void enable(final Instant enableAt) {

      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    }))
        .isInstanceOf(ValidationException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_ACCOUNT_VALIDATOR() throws Exception {
    // WHEN
    ACCOUNT_VALIDATOR.validate(new Account() {
      @Override
      public long getId() {
        return current().nextLong(1L, Long.MAX_VALUE);
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant enableAt) {
      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    });

    // THEN
    log.info("THEN - OK");
  }

  @Test
  public void test_EMAIL_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> EMAIL_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.")
        .hasNoCause();
  }

  @Test
  public void test_EMAIL_VALIDATOR_with_empty() throws Exception {
    assertThatThrownBy(() -> EMAIL_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.")
        .hasNoCause();
  }

  @Test
  public void test_EMAIL_VALIDATOR_with_illegal() throws Exception {
    for (final String email : new String[]{
        "a@b",
        "just.burrow@lul.kr",
        "abcdefghijklmnopqrstuvwxyz0123456789@abcdefghijklmnopqrstuvwxyz0123456789",
        "abcdefghijklmnopqrstuvwxyz0123456789@abcdefghijklmnopqrstuvwxyz0123456789.com",
        "abcdefghijklmnopqrstuvwxyz.0123456789@abcdefghijklmnopqrstuvwxyz0123456789.com",
        "abcdefghijklmnopqrstuvwxyz0123456789@abcdefghijklmnopqrstuvwxyz.0123456789.com",
        "abcdefghijklmnopqrstuvwxyz.0123456789@abcdefghijklmnopqrstuvwxyz.0123456789.com",
        "abcdefghijklmnopqrstuvwxyz+0123456789@abcdefghijklmnopqrstuvwxyz0123456789",
        "abcdefghijklmnopqrstuvwxyz+0123456789@abcdefghijklmnopqrstuvwxyz.0123456789",
        "abcdefghijklmnopqrstuvwxyz+0123456789@abcdefghijklmnopqrstuvwxyz0123456789.com",
        "abcdefghijklmnopqrstuvwxyz+0123456789@abcdefghijklmnopqrstuvwxyz.0123456789.com"
    }) {
      // GIVEN
      log.info("GIVEN - email={}", email);

      // WHEN
      EMAIL_VALIDATOR.validate(email);

      // THEN
      log.info("THEN - OK");
    }
  }

  @Test
  public void test_TOKEN_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> TOKEN_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is null.")
        .hasNoCause();
  }

  @Test
  public void test_TOKEN_VALIDATOR_with_empty() throws Exception {
    assertThatThrownBy(() -> TOKEN_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is empty.")
        .hasNoCause();
  }

  @Test
  public void test_TOKEN_VALIDATOR_with_short() throws Exception {
    // GIVEN
    final String token = randomAlphanumeric(TOKEN_LENGTH - 1);
    log.info("GIVEN - token={}", token);

    // WHEN &  THEN
    assertThatThrownBy(() -> TOKEN_VALIDATOR.validate(token))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("too short token")
        .hasNoCause();
  }

  @Test
  public void test_TOKEN_VALIDATOR_with_long() throws Exception {
    // GIVEN
    final String token = randomAlphanumeric(TOKEN_LENGTH + 1);
    log.info("GIVEN - token={}", token);

    // WHEN & THEN
    assertThatThrownBy(() -> TOKEN_VALIDATOR.validate(token))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("too long token")
        .hasNoCause();
  }

  @Test
  public void test_TOKEN_VALIDATOR() throws Exception {
    // GIVEN
    final String token = randomAlphanumeric(TOKEN_LENGTH);
    log.info("GIVEN - token={}", token);

    // WHEN
    TOKEN_VALIDATOR.validate(token);

    // THEN
    log.info("THEN - OK");
  }

  @Test
  public void test_TTL_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> TTL_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("ttl is null.")
        .hasNoCause();
  }

  @Test
  public void test_TTL_VALIDATOR_with_short() throws Exception {
    // GIVEN
    final Duration ttl = TTL_MIN.minus(1L, NANOS);
    log.info("GIVEN - ttl={}", ttl);

    // WHEN & THEN
    assertThatThrownBy(() -> TTL_VALIDATOR.validate(ttl))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_TTL_VALIDATOR_with_long() throws Exception {
    // GIVEN
    final Duration ttl = TTL_MAX.plus(1L, NANOS);
    log.info("GIVEN - ttl={}", ttl);

    // WHEN & THEN
    assertThatThrownBy(() -> TTL_VALIDATOR.validate(ttl))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_TTL_VALIDATOR_with_min() throws Exception {
    // GIVEN
    log.info("GIVEN - TTL_MIN={}", TTL_MIN);

    // WHEN
    TTL_VALIDATOR.validate(TTL_MIN);

    // THEN
    log.info("THEN - OK");
  }

  @Test
  public void test_TTL_VALIDATOR_with_max() throws Exception {
    // GIVEN
    log.info("GIVEN - TTL_MAX={}", TTL_MAX);

    // WHEN
    TTL_VALIDATOR.validate(TTL_MAX);

    // THEN
    log.info("THEN - OK");
  }

  @Test
  public void test_TTL_VALIDATOR() throws Exception {
    // GIVEN
    final Duration ttl = Duration.of(current().nextLong(TTL_MIN.toMillis(), TTL_MAX.toMillis() + 1L), MILLIS);
    log.info("GIVEN - ttl={}", ttl);

    // WHEN
    TTL_VALIDATOR.validate(ttl);
    TTL_VALIDATOR.validate(ttl());

    // THEN
    log.info("THEN - OK");
  }

  @Test
  public void test_EXPIRE_AT_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> EXPIRE_AT_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }
}
