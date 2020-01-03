package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.*;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/19
 */
public class AccountTest {
  private static final Logger log = getLogger(AccountTest.class);

  @Test
  public void test_nickname_validator_validate_with_null() throws Exception {
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("nickname is null")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, null);
  }

  @Test
  public void test_nickname_validator_validate_with_empty() throws Exception {
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, "");
  }

  @Test
  public void test_nickname_validator_validate_with_single_character() throws Exception {
    // GIVEN
    String nickname;
    do {
      nickname = random(1);
    } while (nickname.matches("\\s"));
    log.info("GIVEN - nickname={}", nickname);

    // WHEN
    NICKNAME_VALIDATOR.validate(nickname);
  }

  @Test
  public void test_nickname_validator_validate() throws Exception {
    // GIVEN
    String nickname;
    do {
      nickname = nickname();
    } while (!nickname.matches(NICKNAME_REGEX));
    log.info("GIVEN - nickname={}", nickname);

    // WHEN
    NICKNAME_VALIDATOR.validate(nickname);
  }

  @Test
  public void test_nickname_validator_validate_with_long() throws Exception {
    // GIVEN
    String nickname;
    do {
      nickname = random(1 + NICKNAME_MAX_LENGTH).trim();
    } while (1 + NICKNAME_MAX_LENGTH != nickname.length());
    log.info("GIVEN - nickname={}", nickname);

    // WHEN & THEN
    final String temp = nickname;
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(temp))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("too long nickname")
        .hasMessageContaining("maxLength=" + NICKNAME_MAX_LENGTH)
        .hasMessageContaining("length=" + (1 + NICKNAME_MAX_LENGTH))
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, nickname);
  }

  @Test
  public void test_nickname_validator_validate_with_leading_space() throws Exception {
    // GIVEN
    final String nickname = " " + nickname(current().nextInt(NICKNAME_MAX_LENGTH));
    log.info("GIVEN - nickname={}", nickname);

    // WHEN & THEN
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(nickname))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("does not match")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, nickname);
  }
}
