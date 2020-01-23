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
import java.util.Set;

import static java.lang.String.format;
import static java.time.temporal.ChronoUnit.*;
import static java.util.stream.Collectors.toSet;
import static kr.lul.common.util.validator.EmailValidator.DEFAULT_DOMAIN_MAX_LENGTH;
import static kr.lul.common.util.validator.EmailValidator.DEFAULT_LOCAL_MAX_LENGTH;

/**
 * 계정 정보 검증 코드.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public interface EnableCode extends Savable<Instant> {
  /**
   * 계정 인증 코드의 상태값.
   *
   * {@link #ISSUED} ➡️ {@link #USED}, {@link #EXPIRED}, {@link #INACTIVE}
   */
  enum Status {
    ISSUED(true, "기본 상태. 사용 가능함.", "USED", "EXPIRED", "INVALID"),
    USED(false, "계정 인증에 사용됨."),
    EXPIRED(false, "유효기간이 지나 만료됨."),
    INACTIVE(false, "사용하지 않은 코드이지만 재발급 등의 이유로 무효화됨.");

    /**
     * 계정정보 검증에 사용할 수 있는지 여부.
     */
    private final boolean valid;
    private final String description;
    private final Set<String> nextNames;
    private Set<Status> nexts;

    private Status(final boolean valid, final String description, final String... nexts) {
      this.valid = valid;
      this.description = description;
      this.nextNames = Set.of(nexts);
    }

    public boolean valid() {
      return this.valid;
    }

    /**
     * @return 설명.
     */
    public String description() {
      return this.description;
    }

    /**
     * @return 가능한 다음 상태. {@code not-null}.
     */
    public Set<Status> nexts() {
      if (null == this.nexts) {
        this.nexts = this.nextNames.stream()
                         .map(Status::valueOf)
                         .collect(toSet());
      }
      return this.nexts;
    }
  }

  String ATTR_ID = "id";
  String ATTR_ACCOUNT = "account";
  String ATTR_EMAIL = "email";
  String ATTR_TOKEN = "token";
  String ATTR_TTL = "ttl";
  String ATTR_EXPIRE_AT = "expireAt";
  String ATTR_STATUS = "status";
  String ATTR_STATUS_AT = "statusAt";
  String ATTR_USED = "used";
  String ATTR_EXPIRED = "expired";
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

  int TOKEN_LENGTH = 64;
  String TOKEN_REGEX = "[a-zA-Z\\d]{" + TOKEN_LENGTH + "}";
  Validator<String> TOKEN_VALIDATOR = new RegexValidator(ATTR_TOKEN, TOKEN_REGEX) {
    @Override
    public void validate(final String token) throws ValidationException {
      if (null == token) {
        throw new ValidationException(ATTR_TOKEN, null, ATTR_TOKEN + " is null.");
      } else if (token.isEmpty()) {
        throw new ValidationException(ATTR_TOKEN, token, ATTR_TOKEN + " is empty.");
      } else if (TOKEN_LENGTH > token.length()) {
        throw new ValidationException(ATTR_TOKEN, token,
            format("too short token : token='%s', token.length=%d, length=%d", token, token.length(), TOKEN_LENGTH));
      } else if (TOKEN_LENGTH < token.length()) {
        throw new ValidationException(ATTR_TOKEN, token,
            format("too long token : token='%s', token.length=%d, length=%d", token, token.length(), TOKEN_LENGTH));
      }
      super.validate(token);
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
  Duration USE_INTERVAL_MIN = Duration.of(10L, SECONDS);

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
  String getToken();

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
   * @return 검증코드 상태.
   */
  Status getStatus();

  /**
   * @return 검증코드가 사용 가능하면 {@code true}.
   */
  default boolean isValid() {
    return getStatus().valid();
  }

  /**
   * @return 상태 변경 시각.
   */
  Instant getStatusAt();

  /**
   * @return 계정 검증 코드가 사용되었는지 여부.
   */
  boolean isUsed();

  /**
   * @return 검증 코드가 만료처리 되었는지 여부.
   */
  boolean isExpired();

  /**
   * {@link #getCreatedAt()} + {@link #USE_INTERVAL_MIN} ~ {@link #getExpireAt()}
   *
   * @return 검증 코드를 사용해 계정을 검증할 수 있는 기간.
   */
  Range<Instant> getValidRange();

  /**
   * 기준 시점에 유효한 코드인지 여부.
   *
   * @param statusAt 기준 시점.
   *
   * @return 만료되었으면 {@code true}.
   */
  boolean isValid(final Instant statusAt);

  /**
   * 검증 코드를 사용해 계정을 검증한다.
   *
   * TODO 상태 변경 메서드 통합.
   *
   * @param now 검증코드를 사용한 시각.
   *
   * @throws EnableCodeStatusException 사용할 수 없는 상태일 떼.
   */
  void use(Instant now) throws EnableCodeStatusException;

  /**
   * 검증 코드를 만료 처리한다.
   *
   * TODO 상태 변경 메서드 통합.
   *
   * @param now 만료 처리 시각.
   *
   * @throws EnableCodeStatusException 만료처리 할 수 없을 때.
   */
  void expire(Instant now) throws EnableCodeStatusException;

  /**
   * 검증 코드를 사용할 수 없도록 비활성화 한다.
   *
   * @param now 비활성화 시각.
   *
   * @throws EnableCodeStatusException 비활성화 처리 할 수 없을 떼.
   */
  void inactive(Instant now) throws EnableCodeStatusException;
}
