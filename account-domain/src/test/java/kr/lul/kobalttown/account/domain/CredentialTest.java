package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.AssertionException;
import org.junit.Test;
import org.slf4j.Logger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Credential.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class CredentialTest {
  private static final Logger log = getLogger(CredentialTest.class);

  @Test
  public void test_validateSecret_with_null() throws Exception {
    assertThatThrownBy(() -> validateSecret(null))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("secret is null");
  }

  @Test
  public void test_validateSecret_with_empty() throws Exception {
    assertThatThrownBy(() -> validateSecret(""))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("too short secret");
  }

  @Test
  public void test_validateSecret_with_min_length_minus_1() throws Exception {
    // Given
    String secret = random(SECRET_MIN_LENGTH - 1);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    assertThatThrownBy(() -> validateSecret(secret))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("too short secret");
  }

  @Test
  public void test_validateSecret_with_min_length() throws Exception {
    // Given
    String secret = random(SECRET_MIN_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    validateSecret(secret);
  }

  @Test
  public void test_validateSecret_with_random() throws Exception {
    // Given
    String secret = random(current().nextInt(SECRET_MIN_LENGTH, 1 + SECRET_MAX_LENGTH));
    log.info("GIVEN - secret={}", secret);

    // When & Then
    validateSecret(secret);
  }

  @Test
  public void test_validateSecret_with_max_length() throws Exception {
    // Given
    String secret = random(SECRET_MAX_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    validateSecret(secret);
  }

  @Test
  public void test_validateSecret_with_max_length_plus_1() throws Exception {
    // Given
    String secret = random(1 + SECRET_MAX_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    assertThatThrownBy(() -> validateSecret(secret))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("too long secret");
  }
}