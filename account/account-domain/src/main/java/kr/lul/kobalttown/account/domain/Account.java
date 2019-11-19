package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.common.util.validator.RegexValidator;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public interface Account {
  String ATTR_ID = "id";
  String ATTR_NICKNAME = "nickname";

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
   */
  Validator<String> NICKNAME_VALIDATOR = new RegexValidator<>(ATTR_NICKNAME, NICKNAME_REGEX) {
    @Override
    public void validate(String nickname) throws ValidationException {
      super.validate(nickname);

      if (NICKNAME_MAX_LENGTH < nickname.length()) {
        throw new ValidationException(ATTR_NICKNAME, nickname,
            format("too long nickname : nickname=%s, length=%d, maxLength=%d",
                singleQuote(nickname), nickname.length(), NICKNAME_MAX_LENGTH));
      }
    }
  };

  /**
   * @return 계정 ID(일련번호).
   */
  long getId();

  /**
   * @return 별명.
   */
  String getNickname();
}
