package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;
import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.account.domain.Account.*;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/19
 */
public class AccountTest {
  private static final Logger log = getLogger(AccountTest.class);

  @Test
  public void test_NICKNAME_VALIDATOR_validate_with_null() throws Exception {
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(null))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("nickname is null")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, null);
  }

  @Test
  public void test_NICKNAME_VALIDATOR_validate_with_empty() throws Exception {
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(""))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, "");
  }

  @Test
  public void test_NICKNAME_VALIDATOR_validate_with_single_character() throws Exception {
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
  public void test_NICKNAME_VALIDATOR_validate() throws Exception {
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
  public void test_NICKNAME_VALIDATOR_validate_with_long() throws Exception {
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
  public void test_NICKNAME_VALIDATOR_validate_with_leading_space() throws Exception {
    // GIVEN
    final String nickname = " " + nickname(current().nextInt(NICKNAME_MAX_LENGTH));
    log.info("GIVEN - nickname={}", nickname);

    // WHEN & THEN
    assertThatThrownBy(() -> NICKNAME_VALIDATOR.validate(nickname))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("illegal nickname pattern")
        .hasMessageContaining("nickname='" + nickname + "'")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, nickname);
  }

  @Test
  public void test_NICKNAME_VALIDATOR_with_contains_illegal_characters() throws Exception {
    // GIVEN
    final List<String> nicknames = NICKNAME_ILLEGAL_CHARACTERS
                                       .stream()
                                       .map(c -> randomAlphanumeric(1, 5) + c + randomAlphanumeric(1, 5))
                                       .collect(toList());
    log.info("GIVEN - nicknames={}", nicknames);

    for (final String nickname : nicknames) {
      log.info("GIVEN - nickname={}", nickname);

      // WHEN
      final ValidationException ex = catchThrowableOfType(() -> NICKNAME_VALIDATOR.validate(nickname), ValidationException.class);
      log.info("WHEN - ex=" + ex, ex);

      // THEN
      assertThat(ex)
          .isNotNull()
          .hasMessageStartingWith("nickname contains illegal character")
          .hasMessageContaining("nickname=" + singleQuote(nickname))
          .hasMessageContaining("illegalCharacter=\\u");
    }
  }
}
