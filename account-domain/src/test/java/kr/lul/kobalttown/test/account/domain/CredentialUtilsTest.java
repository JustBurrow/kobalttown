package kr.lul.kobalttown.test.account.domain;

import org.junit.Test;
import org.slf4j.Logger;

import static kr.lul.kobalttown.account.domain.Credential.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class CredentialUtilsTest {
  private static final Logger log = getLogger(CredentialUtilsTest.class);

  @Test
  public void test_password() throws Exception {
    for (int i = 0; i < 10000; i++) {
      // When
      String password = CredentialUtils.password();

      // Then
      assertThat(password)
          .isNotNull();
      assertThat(password.length())
          .as("password.length={}, password={}", password.length(), password)
          .isGreaterThanOrEqualTo(SECRET_MIN_LENGTH)
          .isLessThanOrEqualTo(SECRET_MAX_LENGTH);
      validateSecret(password);
    }
  }
}