package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.ValidationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

  private PasswordEncoder passwordEncoder;

  @Before
  public void setUp() throws Exception {
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Test
  public void test_validateSecret_with_null() throws Exception {
    assertThatThrownBy(() -> validateSecret(null))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("secret is null");
  }

  @Test
  public void test_validateSecret_with_empty() throws Exception {
    assertThatThrownBy(() -> validateSecret(""))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too short secret");
  }

  @Test
  public void test_validateSecret_with_min_length_minus_1() throws Exception {
    // Given
    String secret = random(SECRET_MIN_LENGTH - 1);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    assertThatThrownBy(() -> validateSecret(secret))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too short secret");
  }

  @Test
  public void test_validateSecret_with_min_length() throws Exception {
    // Given
    String secret = random(SECRET_MIN_LENGTH);
    String hash = this.passwordEncoder.encode(secret);
    log.info("GIVEN - secret={}, hash={}", secret, hash);

    // When & Then
    validateSecret(hash);
  }

  @Test
  public void test_validateSecret_with_random() throws Exception {
    // Given
    String secret = random(current().nextInt(SECRET_MIN_LENGTH, 1 + SECRET_MAX_LENGTH));
    String hash = this.passwordEncoder.encode(secret);
    log.info("GIVEN - secret={}, hash={}", secret, hash);

    // When & Then
    validateSecret(hash);
  }

  @Test
  public void test_validateSecret_with_max_length() throws Exception {
    // Given
    String secret = random(SECRET_MAX_LENGTH);
    String hash = this.passwordEncoder.encode(secret);
    log.info("GIVEN - secret={}, hash={}", secret, hash);

    // When & Then
    validateSecret(hash);
  }

  @Test
  public void test_validateSecret_with_max_length_plus_1() throws Exception {
    // Given
    String secret = random(1 + SECRET_MAX_LENGTH);
    log.info("GIVEN - secret={}", secret);

    // When & Then
    assertThatThrownBy(() -> validateSecret(secret))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too long secret");
  }

  @Test
  public void test_validateSecret_with_illegal_pattern() throws Exception {
    // Given
    String temp;
    do {
      temp = RandomStringUtils.random(current().nextInt(SECRET_MIN_LENGTH, 1 + SECRET_MAX_LENGTH));
    } while (temp.matches(SECRET_HASH_REGEX));
    final String secret = temp;
    log.info("GIVEN - secret={}", secret);

    // When & Then
    assertThatThrownBy(() -> validateSecret(secret))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("illegal secret hash pattern");
  }
}