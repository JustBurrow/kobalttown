package kr.lul.kobalttown.account.domain;

import kr.lul.common.data.Creatable;
import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.common.util.validator.RegexValidator;

import java.time.Instant;

import static java.lang.String.format;

/**
 * 계정 인증 정보.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public interface Credential extends Creatable<Instant> {
  String ATTR_ID = "id";
  String ATTR_ACCOUNT = "account";
  /**
   * 종류 :
   * <ul>
   *   <li>사용자가 임의로 지정하는 {@link Credential#ATTR_USER_KEY userKey}</li>
   *   <li>E-Mail address.</li>
   * </ul>
   */
  String ATTR_PUBLIC_KEY = "publicKey";
  String ATTR_SECRET_HASH = "secretHash";
  String ATTR_CREATED_AT = "createdAt";

  /**
   * 사용자가 지정하는 {@link Credential#getPublicKey()}의 한 종류.
   */
  String ATTR_USER_KEY = "userKey";
  /**
   * 비밀번호.
   */
  String ATTR_SECRET = "secret";

  Validator<Account> ACCOUNT_VALIDATOR = account -> {
    if (null == account) {
      throw new ValidationException(ATTR_ACCOUNT, null, "account is null.");
    } else if (0L >= account.getId()) {
      throw new ValidationException(ATTR_ACCOUNT, account,
          format("illegal account id : %s.id=%d", ATTR_ACCOUNT, account.getId()));
    }
  };

  int PUBLIC_KEY_MIN_LENGTH = 3;
  int PUBLIC_KEY_MAX_LENGTH = 255;

  String PUBLIC_KEY_REGEX = "\\S(.*\\S)?";

  Validator<String> PUBLIC_KEY_VALIDATOR = new RegexValidator(ATTR_PUBLIC_KEY, PUBLIC_KEY_REGEX) {
    @Override
    public void validate(final String publicKey) throws ValidationException {
      if (null == publicKey) {
        throw new ValidationException(ATTR_PUBLIC_KEY, null, ATTR_PUBLIC_KEY + " is null.");
      } else if (publicKey.isEmpty()) {
        throw new ValidationException(ATTR_PUBLIC_KEY, publicKey, ATTR_PUBLIC_KEY + " is empty.");
      } else if (PUBLIC_KEY_MIN_LENGTH > publicKey.length()) {
        throw new ValidationException(ATTR_PUBLIC_KEY, publicKey, format("too short %s : min=%d, %s.length=%d",
            ATTR_PUBLIC_KEY, PUBLIC_KEY_MIN_LENGTH, ATTR_PUBLIC_KEY, publicKey.length()));
      } else if (PUBLIC_KEY_MAX_LENGTH < publicKey.length()) {
        throw new ValidationException(ATTR_PUBLIC_KEY, publicKey, format("too long %s : max=%d, %s.length=%d",
            ATTR_PUBLIC_KEY, PUBLIC_KEY_MAX_LENGTH, ATTR_PUBLIC_KEY, publicKey.length()));
      }

      super.validate(publicKey);
    }
  };

  /**
   * 비밀번호 최소 길이.
   */
  int SECRET_MIN_LENGTH = 4;
  /**
   * 비밀번호 최대 길이.
   */
  int SECRET_MAX_LENGTH = 60;

  /**
   * 유효한 비공개키인지 확인한다.
   */
  Validator<String> SECRET_VALIDATOR = secret -> {
    if (null == secret) {
      throw new ValidationException("secret", null, "secret is null.");
    } else if (secret.isEmpty()) {
      throw new ValidationException("secret", "", "secret is empty.");
    } else if (SECRET_MIN_LENGTH > secret.length()) {
      throw new ValidationException("secret", "[ PROTECTED ]", "too short secret.");
    } else if (SECRET_MAX_LENGTH < secret.length()) {
      throw new ValidationException("secret", "[ PROTECTED ]", "too long secret.");
    }
  };

  /**
   * 유효한 비공개키 해시 패턴. BCrypt를 사용한다.
   */
  String SECRET_HASH_REGEX = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

  /**
   * 유효한 비공개키 해시인지 확인한다.
   */
  Validator<String> SECRET_HASH_VALIDATOR = new RegexValidator(ATTR_SECRET_HASH, SECRET_HASH_REGEX) {
    @Override
    public void validate(final String secretHash) throws ValidationException {
      if (null == secretHash)
        throw new ValidationException(ATTR_SECRET_HASH, null, ATTR_SECRET_HASH + " is null.");
      else if (secretHash.isEmpty())
        throw new ValidationException(ATTR_SECRET_HASH, secretHash, ATTR_SECRET_HASH + " is empty.");

      super.validate(secretHash);
    }
  };

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
