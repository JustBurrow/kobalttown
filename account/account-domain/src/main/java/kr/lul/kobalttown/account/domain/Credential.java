package kr.lul.kobalttown.account.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.common.util.validator.RegexValidator;

import static java.lang.String.format;

/**
 * 계정 인증 정보.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public interface Credential {
  String ATTR_ID = "id";
  String ATTR_ACCOUNT = "account";
  String ATTR_PUBLIC_KEY = "publicKey";
  String ATTR_SECRET_HASH = "secretHash";

  Validator<Account> ACCOUNT_VALIDATOR = account -> {
    if (null == account) {
      throw new ValidationException(ATTR_ACCOUNT, null, "account is null.");
    } else if (0L >= account.getId()) {
      throw new ValidationException(ATTR_ACCOUNT, account,
          format("illegal account id : %s.id=%d", ATTR_ACCOUNT, account.getId()));
    }
  };

  int PUBLIC_KEY_MAX_LENGTH = 256;

  String PUBLIC_KEY_REGEX = "\\S(.*\\S)?";

  Validator<String> PUBLIC_KEY_VALIDATOR = new RegexValidator<>(ATTR_PUBLIC_KEY, PUBLIC_KEY_REGEX) {
    @Override
    public void validate(String publicKey) throws ValidationException {
      super.validate(publicKey);

      if (PUBLIC_KEY_MAX_LENGTH < publicKey.length()) {
        throw new ValidationException(ATTR_PUBLIC_KEY, publicKey, format("%s is too long : max=%d, %s.length=%d",
            ATTR_PUBLIC_KEY, PUBLIC_KEY_MAX_LENGTH, ATTR_PUBLIC_KEY, publicKey.length()));
      }
    }
  };

  /**
   * 비밀번호 최소 길이.
   */
  int SECRET_MIN_LENGTH = 4;
  /**
   * 비밀번호 최대 길이.
   */
  int SECRET_MAX_LENGTH = 256;

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
  Validator<String> SECRET_HASH_VALIDATOR = new RegexValidator<>(ATTR_SECRET_HASH, SECRET_HASH_REGEX);

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
