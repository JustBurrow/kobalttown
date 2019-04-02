package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.Creatable;

import static java.lang.String.format;

/**
 * 계정 인증 정보.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public interface Credential extends Creatable {
  /**
   * 비밀번호 최소 길이.
   */
  int SECRET_MIN_LENGTH = 4;
  /**
   * 비밀번호 최대 길이.
   */
  int SECRET_MAX_LENGTH = 256;
  /**
   * 유효한 비밀번호 해시 패턴.
   */
  String SECRET_HASH_REGEX = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

  static void validateSecret(String secret) {
    if (null == secret) {
      throw new AssertionException("secret is null.");
    } else if (SECRET_MIN_LENGTH > secret.length()) {
      throw new AssertionException(format("too short secret : length=%d, min=%d", secret.length(), SECRET_MIN_LENGTH));
    } else if (SECRET_MAX_LENGTH < secret.length()) {
      throw new AssertionException(format("too long secret : length=%d, max=%d", secret.length(), SECRET_MAX_LENGTH));
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