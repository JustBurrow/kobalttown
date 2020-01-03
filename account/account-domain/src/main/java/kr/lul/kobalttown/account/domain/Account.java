package kr.lul.kobalttown.account.domain;

import kr.lul.common.data.Savable;
import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.common.util.validator.RegexValidator;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public interface Account extends Savable<Instant> {
  String ATTR_ID = "id";
  String ATTR_NICKNAME = "nickname";
  String ATTR_CREATED_AT = "createdAt";
  String ATTR_UPDATED_AT = "updatedAt";

  /**
   * {@link #getNickname()}의 최대 길이.
   */
  int NICKNAME_MAX_LENGTH = 64;
  /**
   * 사용 가능한 {@link #getNickname()}의 패턴.
   */
  String NICKNAME_REGEX = "\\S(.*\\S)?";

  /**
   * 사용 가능한 별명인지 확인한다.
   */
  Validator<String> NICKNAME_VALIDATOR = new RegexValidator<>(ATTR_NICKNAME, NICKNAME_REGEX) {
    @Override
    public void validate(final String nickname) throws ValidationException {
      if (null == nickname) {
        throw new ValidationException(ATTR_NICKNAME, null, ATTR_NICKNAME + " is null.");
      } else if (nickname.isEmpty()) {
        throw new ValidationException(ATTR_NICKNAME, nickname, ATTR_NICKNAME + " is empty.");
      } else if (NICKNAME_MAX_LENGTH < nickname.length()) {
        throw new ValidationException(ATTR_NICKNAME, nickname,
            format("too long nickname : nickname=%s, length=%d, maxLength=%d",
                singleQuote(nickname), nickname.length(), NICKNAME_MAX_LENGTH));
      }

      super.validate(nickname);
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

  /**
   * 계정 등록시의 정보를 검증 받았는지 여부.
   * 지금도 사용할 수 있는지와는 무관한다.
   *
   * @return 계정 활성화 여부.
   */
  boolean isEnabled();
}
