package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Credential.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/19
 */
public class CredentialTest {
  private static final Logger log = getLogger(CredentialTest.class);

  @Test
  public void test_ACCOUNT_VALIDATOR_validate_with_null() throws Exception {
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .extracting("targetName", "target")
        .containsSequence(ATTR_ACCOUNT, null);
  }

  @Test
  public void test_ACCOUNT_VALIDATOR_validate_with_not_persisted_account() throws Exception {
    // GIVEN
    final Account account = new Account() {
      @Override
      public Instant getUpdatedAt() {
        return null;
      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public long getId() {
        return 0L;
      }

      @Override
      public String getNickname() {
        return "nickname";
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant updatedAt) {

      }
    };
    log.info("GIVEN - account.id={}", account.getId());

    // WHEN & THEN
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(account))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_ACCOUNT, account);
  }

  @Test
  public void test_ACCOUNT_VALIDATOR_validate() throws Exception {
    // GIVEN
    final Account account = new Account() {
      @Override
      public Instant getUpdatedAt() {
        return null;
      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public long getId() {
        return 1L + current().nextLong(1, Long.MAX_VALUE);
      }

      @Override
      public String getNickname() {
        return "nickname";
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant updatedAt) {

      }
    };
    log.info("GIVEN - account.id={}", account.getId());

    // WHEN
    ACCOUNT_VALIDATOR.validate(account);
  }

  @Test
  public void test_PUBLIC_KEY_VALIDATOR_validate_null() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> PUBLIC_KEY_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_PUBLIC_KEY, null);
  }

  @Test
  public void test_PUBLIC_KEY_VALIDATOR_validate_empty() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> PUBLIC_KEY_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_PUBLIC_KEY, "");
  }

  @Test
  public void test_PUBLIC_KEY_VALIDATOR_validate() throws Exception {
    // GIVEN
    final String publicKey = random(current().nextInt(1, 1 + PUBLIC_KEY_MAX_LENGTH)).trim();
    log.info("GIVEN - publicKey={}", publicKey);

    // WHEN
    PUBLIC_KEY_VALIDATOR.validate(publicKey);
  }

  @Test
  public void test_PUBLIC_KEY_VALIDATOR_validate_with_long() throws Exception {
    // GIVEN
    String publicKey;
    do {
      publicKey = random(1 + PUBLIC_KEY_MAX_LENGTH).trim();
    } while (PUBLIC_KEY_MAX_LENGTH >= publicKey.length());
    log.info("GIVEN - publicKey={}", publicKey);

    // WHEN & THEN
    final String temp = publicKey;
    assertThatThrownBy(() -> PUBLIC_KEY_VALIDATOR.validate(temp))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_PUBLIC_KEY, publicKey);
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_null() throws Exception {
    assertThatThrownBy(() -> SECRET_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("secret is null.")
        .hasNoCause();
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_empty() throws Exception {
    assertThatThrownBy(() -> SECRET_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("secret is empty.")
        .hasNoCause();
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_short() throws Exception {
    // GIVEN
    final String secret = random(SECRET_MIN_LENGTH - 1);
    log.info("GIVEN - secret={}", secret);

    // WHEN & THEN
    assertThatThrownBy(() -> SECRET_VALIDATOR.validate(secret))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too short secret")
        .hasMessageNotContaining(secret)
        .hasNoCause();
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_long() throws Exception {
    // GIVEN
    final String secret = random(SECRET_MAX_LENGTH + 1);
    log.info("GIVEN - secret={}", secret);

    // WHEN & THEN
    assertThatThrownBy(() -> SECRET_VALIDATOR.validate(secret))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too long secret")
        .hasMessageNotContaining(secret)
        .hasNoCause();
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_min() throws Exception {
    // GIVEN
    final String secret = random(SECRET_MIN_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // WHEN
    SECRET_VALIDATOR.validate(secret);
    log.info("WHEN - no exception");
  }

  @Test
  public void test_SECRET_VALIDATOR_validate_with_max() throws Exception {
    // GIVEN
    final String secret = random(SECRET_MAX_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // WHEN
    SECRET_VALIDATOR.validate(secret);
    log.info("WHEN - no exception");
  }

  @Test
  public void test_SECRET_VALIDATOR_validate() throws Exception {
    // GIVEN
    final String secret = random(current().nextInt(SECRET_MIN_LENGTH, SECRET_MAX_LENGTH + 1));
    log.info("GIVEN - secret={}", secret);

    // WHEN
    SECRET_VALIDATOR.validate(secret);
    log.info("WHEN - no exception.");
  }
}
