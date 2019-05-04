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
  /**
   * {@link #getNickname()}의 최대 길이.
   */
  int NICKNAME_MAX_LENGTH = 20;
  /**
   * 사용 가능한 {@link #getNickname()}의 패턴.
   */
  String NICKNAME_REGEX = "\\S(.*\\S)?";

  /**
   * 사용 가능한 별명인지 확인한다.
   *
   * @param nickname 별명.
   *
   * @throws AssertionException 사용할 수 없는 별명일 때.
   */
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

  /**
   * 사용 가능한 별명인지 확인한다.
   *
   * @param nickname 별명.
   *
   * @return 사용 가능하면 {@code true}.
   */
  static boolean isValidateNickname(String nickname) {
    return null != nickname &&
        !nickname.isEmpty() &&
        NICKNAME_MAX_LENGTH >= nickname.length() &&
        nickname.matches(NICKNAME_REGEX);
  }

  /**
   * @return 계정 ID(일련번호).
   */
  int getId();

  /**
   * @return 별명.
   */
  String getNickname();
}