package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.Creatable;
import kr.lul.kobalttown.common.util.ValidationException;

import static java.lang.String.format;

/**
 * 계정 인증 정보.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public interface Credential extends Creatable {
  String ATTR_SECRET = "secret";
  /**
   * 비밀번호 최소 길이.
   */
  int SECRET_MIN_LENGTH = 4;
  /**
   * 비밀번호 최대 길이.
   */
  int SECRET_MAX_LENGTH = 256;
  /**
   * 유효한 비공개키 해시 패턴. BCrypt를 사용한다.
   */
  String SECRET_HASH_REGEX = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

  /**
   * 유효한 비공개키 해시인지 확인한다.
   *
   * @param secret 비공개키 해시.
   *
   * @throws ValidationException 비공개키 해시가 아닐 경우.
   */
  static void validateSecret(String secret) throws ValidationException {
    if (null == secret) {
      throw new ValidationException(ATTR_SECRET, "secret is null.");
    } else if (SECRET_MIN_LENGTH > secret.length()) {
      throw new ValidationException(ATTR_SECRET,
          format("too short secret : length=%d, min=%d", secret.length(), SECRET_MIN_LENGTH));
    } else if (SECRET_MAX_LENGTH < secret.length()) {
      throw new ValidationException(ATTR_SECRET,
          format("too long secret : length=%d, max=%d", secret.length(), SECRET_MAX_LENGTH));
    } else if (!secret.matches(SECRET_HASH_REGEX)) {
      throw new ValidationException(ATTR_SECRET,
          format("illegal secret hash pattern : secret=[ PROTECTED ], pattern='%s'", SECRET_HASH_REGEX));
    }
  }

  /**
   * @return 일련번호.
   */
  long getId();

  /**
   * @return 소유 계정.
   */
  Account getAccount();

  /**
   * @return 공개키(평문).
   */
  String getPublicKey();

  /**
   * @return 비밀번호 해시.
   */
  String getSecretHash();
}