package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.AssertionException;
import org.junit.Test;
import org.slf4j.Logger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class AccountTest {
  private static final Logger log = getLogger(AccountTest.class);

  @Test
  public void test_validateNickname_with_null() throws Exception {
    assertThatThrownBy(() -> validateNickname(null))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("nickname is null");
    assertThat(isValidateNickname(null))
        .isFalse();
  }

  @Test
  public void test_validateNickname_with_empty() throws Exception {
    assertThatThrownBy(() -> validateNickname(""))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("nickname is empty");
    assertThat(isValidateNickname(""))
        .isFalse();
  }

  @Test
  public void test_validateNickname_with_space() throws Exception {
    assertThatThrownBy(() -> validateNickname(" "))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("illegal nickname pattern");
    assertThat(isValidateNickname(" "))
        .isFalse();
  }

  @Test
  public void test_validateNickname_with_space_prefix() throws Exception {
    // Given
    final String nickname = " " + random(current().nextInt(NICKNAME_MAX_LENGTH));
    log.info("GIVEN - nickname={}", nickname);

    // When & Then
    assertThatThrownBy(() -> validateNickname(nickname))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("illegal nickname pattern");
    assertThat(isValidateNickname(nickname))
        .isFalse();
  }

  @Test
  public void test_validateNickname_with_space_postfix() throws Exception {
    // Given
    final String nickname = random(current().nextInt(NICKNAME_MAX_LENGTH)) + " ";
    log.info("GIVEN - nickname={}", nickname);

    // When & Then
    assertThatThrownBy(() -> validateNickname(nickname))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("illegal nickname pattern");
    assertThat(isValidateNickname(nickname))
        .isFalse();
  }

  @Test
  public void test_validateNickname_with_max_length() throws Exception {
    // Given
    String nickname;
    do {
      nickname = random(NICKNAME_MAX_LENGTH);
    } while (!nickname.matches(NICKNAME_REGEX));
    log.info("GIVEN - nickname={}", nickname);

    // When & Then
    validateNickname(nickname);
    assertThat(isValidateNickname(nickname))
        .isTrue();
  }

  @Test
  public void test_validateNickname_with_max_length_plus_1() throws Exception {
    // Given
    var ref = new Object() {
      String nickname;
    };
    do {
      ref.nickname = random(NICKNAME_MAX_LENGTH);
    } while (!ref.nickname.matches(NICKNAME_REGEX));
    ref.nickname += "a";
    log.info("GIVEN - nickname={}", ref.nickname);

    // When & Then
    assertThatThrownBy(() -> validateNickname(ref.nickname))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("too long nickname");
    assertThat(isValidateNickname(ref.nickname))
        .isFalse();
  }
}