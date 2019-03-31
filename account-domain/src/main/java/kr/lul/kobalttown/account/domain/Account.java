package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.SimpleToString;
import kr.lul.kobalttown.common.util.Updatable;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public interface Account extends Updatable, SimpleToString {
  int NICKNAME_MAX_LENGTH = 20;
  String NICKNAME_REGEX = "\\S(.*\\S)?";

  static void validateNickname(String nickname) throws AssertionException {
    if (null == nickname) {
      throw new AssertionException("nickname is null.");
    } else if (nickname.isEmpty()) {
      throw new AssertionException("nickname is empty.");
    } else if (!nickname.matches(NICKNAME_REGEX)) {
      throw new AssertionException(format("illegal nickname pattern : nickname='%s', pattern='%s'",
          nickname, NICKNAME_REGEX));
    } else if (NICKNAME_MAX_LENGTH < nickname.length()) {
      throw new AssertionException(format("too long nickname : nickname=%s, length=%d, maxLength=%d",
          singleQuote(nickname), nickname.length(), NICKNAME_MAX_LENGTH));
    }
  }

  int getId();

  String getNickname();
}