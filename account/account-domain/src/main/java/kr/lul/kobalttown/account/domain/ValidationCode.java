package kr.lul.kobalttown.account.domain;

import kr.lul.common.data.Savable;
import kr.lul.common.util.ContinuousRange;
import kr.lul.common.util.Range;
import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.common.util.validator.EmailValidator;
import kr.lul.common.util.validator.RangeValidator;
import kr.lul.common.util.validator.RegexValidator;

import java.time.Duration;
import java.time.Instant;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.*;
import static kr.lul.common.util.validator.EmailValidator.DEFAULT_DOMAIN_MAX_LENGTH;
import static kr.lul.common.util.validator.EmailValidator.DEFAULT_LOCAL_MAX_LENGTH;

/**
 * 계정 정보 검증 코드.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public interface ValidationCode extends Savable<Instant> {
  String ATTR_ID = "id";
  String ATTR_ACCOUNT = "account";
  String ATTR_EMAIL = "email";
  String ATTR_CODE = "code";
  String ATTR_TTL = "ttl";
  String ATTR_EXPIRE_AT = "expireAt";
  String ATTR_USED = "used";
  String ATTR_USED_AT = "usedAt";
  String ATTR_EXPIRED = "expired";
  String ATTR_EXPIRED_AT = "expiredAt";
  String ATTR_CREATED_AT = "createdAt";
  String ATTR_UPDATED_AT = "updatedAt";

  Validator<Account> ACCOUNT_VALIDATOR = account -> {
    if (null == account) {
      throw new ValidationException(ATTR_ACCOUNT, null, ATTR_ACCOUNT + " is null.");
    } else if (0L >= account.getId()) {
      throw new ValidationException(ATTR_ACCOUNT, account,
          format("illegal account id : %s.id=%d", ATTR_ACCOUNT, account.getId()));
    } else if (account.isEnabled()) {
      throw new ValidationException(ATTR_ACCOUNT, account, "already enabled account.");
    }
  };

  int EMAIL_MAX_LENGTH = DEFAULT_LOCAL_MAX_LENGTH + DEFAULT_DOMAIN_MAX_LENGTH + 1;
  EmailValidator EMAIL_VALIDATOR = new EmailValidator();

  int CODE_LENGTH = 64;
  String CODE_REGEX = "[a-zA-Z\\d]{" + CODE_LENGTH + "}";
  Validator<String> CODE_VALIDATOR = new RegexValidator(ATTR_CODE, CODE_REGEX) {
    @Override
    public void validate(final String code) throws ValidationException {
      if (null == code) {
        throw new ValidationException(ATTR_CODE, null, ATTR_CODE + " is null.");
      } else if (code.isEmpty()) {
        throw new ValidationException(ATTR_CODE, code, ATTR_CODE + " is empty.");
      } else if (CODE_LENGTH > code.length()) {
        throw new ValidationException(ATTR_CODE, code,
            format("too short code : code='%s', code.length=%d, length=%d", code, code.length(), CODE_LENGTH));
      } else if (CODE_LENGTH < code.length()) {
        throw new ValidationException(ATTR_CODE, code,
            format("too long code : code='%s', code.length=%d, length=%d", code, code.length(), CODE_LENGTH));
      }
      super.validate(code);
    }
  };

  /**
   * 검증 코드의 기본 유효시간. 10 min.
   */
  Duration TTL_DEFAULT = Duration.of(10L, MINUTES);
  /**
   * 검증 코드의 최소 유효시간. 3 min.
   */
  Duration TTL_MIN = Duration.of(3L, MINUTES);
  /**
   * 검증 코드의 최대 유효시간. 2 hour.
   */
  Duration TTL_MAX = Duration.of(2L, HOURS);

  Validator<Duration> TTL_VALIDATOR = new RangeValidator<>(ATTR_TTL,
      new ContinuousRange<>(TTL_MIN, true, TTL_MAX, true)) {
    @Override
    public void validate(final Duration ttl) throws ValidationException {
      if (null == ttl) {
        throw new ValidationException(ATTR_TTL, null, "ttl is null.");
      }
      super.validate(ttl);
    }
  };

  Validator<Instant> EXPIRE_AT_VALIDATOR = expireAt -> {
    if (null == expireAt) {
      throw new ValidationException(ATTR_EXPIRE_AT, null, ATTR_EXPIRE_AT + " is null.");
    }
  };

  /**
   * 계정 등록 후 검증코드 사용까지 최소 시간.
   */
  Duration MIN_USE_INTERVAL = Duration.of(10L, SECONDS);

  /**
   * @return ID.
   */
  long getId();

  /**
   * @return 검증할 계정.
   */
  Account getAccount();

  /**
   * @return 검증코드 수신자.
   */
  String getEmail();

  /**
   * 계정 사용자에게 전달할 검증 코드.
   *
   * @return 검증 코드.
   */
  String getCode();

  /**
   * @return 유효기간.
   */
  default Duration getTtl() {
    return TTL_DEFAULT;
  }

  /**
   * @return 만료 시각.
   */
  Instant getExpireAt();

  /**
   * @return 계정 검증 코드가 사용되었는지 여부.
   */
  default boolean isUsed() {
    return null != getUsedAt();
  }

  /**
   * @return 계정 검증 코드를 사용했는지 여부. 사용하지 않은 경우 {@code null}.
   */
  Instant getUsedAt();

  /**
   * @return 검증 코드가 만료처리 되었는지 여부.
   */
  default boolean isExpired() {
    return null != getExpiredAt();
  }

  /**
   * @return 만료처리된 시각. 아직 유효한 경우에는 {@code null}.
   */
  Instant getExpiredAt();

  Range<Instant> getValidRange();

  /**
   * 기준 시점에 유효한 코드인지 여부.
   *
   * @param when 기준 시점.
   *
   * @return 만료되었으면 {@code true}.
   */
  boolean isValid(final Instant when);

  /**
   * 검증 코드를 사용해 계정을 검증한다.
   *
   * @param when 검증코드를 사용한 시각.
   *
   * @throws IllegalStateException 검증 코드를 사용할 수 없는 경우.
   */
  void use(Instant when) throws IllegalStateException;

  /**
   * 유효기간이 지났을 경우에 만료 처리.
   *
   * @param when 기준 시점.
   *
   * @throws IllegalStateException 만료처리를 할 수 없을 경우.
   */
  void expire(Instant when) throws IllegalStateException;
}
