package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

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
  public void test_account_validator_validate_with_null() throws Exception {
    assertThatThrownBy(() -> ACCOUNT_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .extracting("targetName", "target")
        .containsSequence(ATTR_ACCOUNT, null);
  }

  @Test
  public void test_account_validator_validate_with_not_persisted_account() throws Exception {
    // GIVEN
    Account account = new Account() {
      @Override
      public long getId() {
        return 0L;
      }

      @Override
      public String getNickname() {
        return "nickname";
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
  public void test_account_validator_validate() throws Exception {
    // GIVEN
    Account account = new Account() {
      @Override
      public long getId() {
        return 1L + current().nextLong(1, Long.MAX_VALUE);
      }

      @Override
      public String getNickname() {
        return "nickname";
      }
    };
    log.info("GIVEN - account.id={}", account.getId());

    // WHEN
    ACCOUNT_VALIDATOR.validate(account);
  }

  @Test
  public void test_public_key_validator_validate_null() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> PUBLIC_KEY_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_PUBLIC_KEY, null);
  }

  @Test
  public void test_public_key_validator_validate_empty() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> PUBLIC_KEY_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_PUBLIC_KEY, "");
  }

  @Test
  public void test_public_key_validator_validate() throws Exception {
    // GIVEN
    String publicKey = random(current().nextInt(1, 1 + PUBLIC_KEY_MAX_LENGTH)).trim();
    log.info("GIVEN - publicKey={}", publicKey);

    // WHEN
    PUBLIC_KEY_VALIDATOR.validate(publicKey);
  }

  @Test
  public void test_public_key_validator_validate_with_long() throws Exception {
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
}
