package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.Range;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.AccountFactoryImpl;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.domain.ValidationCodeStatusException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static java.lang.System.currentTimeMillis;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.NANOS;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.ValidationCode.Status.*;
import static kr.lul.kobalttown.account.domain.ValidationCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.ValidationCode.USE_INTERVAL_MIN;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class ValidationCodeEntityTest {
  private static final Logger log = getLogger(ValidationCodeEntityTest.class);

  private AccountFactory accountFactory;
  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.accountFactory = new AccountFactoryImpl();
    this.before = Instant.ofEpochMilli(currentTimeMillis());
  }

  @Test
  public void test_new_with_null_account_and_expireAt() throws Exception {
    assertThatThrownBy(() -> new ValidationCodeEntity(null, email(), code(), this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_account_and_ttl() throws Exception {
    assertThatThrownBy(() -> new ValidationCodeEntity(null, email(), code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_enabled_account_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), true, this.before),
            email(), code(), this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_enabled_account_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), true, this.before),
            email(), code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_email() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            null, code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_new_with_empty_email() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            "", code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_new_with_null_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_code_and_null_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), (Duration) null, this.before))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_new_with_code_and_0_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), Duration.ZERO, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), (Instant) null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_expireAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), this.before.plus(ttl()), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_ttl_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), ttl(), null))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_new_with_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(ttl());
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final ValidationCode validationCode = new ValidationCodeEntity(account, email, code, expireAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            code, expireAt, ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_new_with_ttl() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Duration ttl = ttl();
    log.info("GIVEN - ttl={}", ttl);

    // WHEN
    final ValidationCode validationCode = new ValidationCodeEntity(account, email, code, ttl, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email, code, this.before.plus(ttl), ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_isValid_with_null() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.isValid(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("now is null.")
        .hasNoCause();
  }

  @Test
  public void test_isValid_with_before_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(USE_INTERVAL_MIN).minusNanos(1L)))
        .isFalse();
  }

  @Test
  public void test_isValid_with_min_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(USE_INTERVAL_MIN)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_after_min_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(USE_INTERVAL_MIN).plusNanos(1L)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt()))
        .isTrue();
  }

  @Test
  public void test_isValid_with_before_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt().minusNanos(1L)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_after_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt().plusNanos(1L)))
        .isFalse();
  }

  @Test
  public void test_isValid_used() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    code.use(code.getExpireAt());
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt()))
        .isFalse();
  }

  @Test
  public void test_use_with_null() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("now is null.")
        .hasNoCause();
  }

  @Test
  public void test_use_with_before_createdAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant now = this.before.minus(1L, NANOS);
    log.info("GIVEN - now={}", now);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(now))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("now=" + now)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_after_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String email = email();
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, email, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    final Instant now = expireAt.plus(1L, MILLIS);
    log.info("GIVEN - now={}", now);

    // WHEN
    final ValidationCodeStatusException ex = catchThrowableOfType(() -> validationCode.use(now),
        ValidationCodeStatusException.class);
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .hasMessage("invalidated at " + now)
        .hasNoCause();
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, ValidationCode::getUpdatedAt)
        .containsSequence(account, email,
            code, expireAt, EXPIRED, now,
            false, true, now);
    assertThat(account)
        .extracting(Account::isEnabled, Updatable::getUpdatedAt)
        .containsSequence(false, this.before);
  }

  @Test
  public void test_use_with_createdAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("now=" + this.before)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_before_min_interval() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant now = this.before.plus(USE_INTERVAL_MIN).minus(1L, NANOS);
    log.info("GIVEN - now={}", now);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(now))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("now=" + now)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_min_interval() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String email = email();
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, email, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    final Instant now = this.before.plus(USE_INTERVAL_MIN);
    log.info("GIVEN - now={}", now);

    // WHEN
    validationCode.use(now);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, email,
            code, expireAt, USED, now,
            true, false, this.before, now);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, now);
  }

  @Test
  public void test_use_with_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String email = email();
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, email, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    // WHEN
    validationCode.use(expireAt);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, email,
            code, expireAt, USED, expireAt,
            true, false, this.before, expireAt);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, expireAt);
  }

  @Test
  public void test_use_with_enabled_account() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final ValidationCode code = new ValidationCodeEntity(account, email(), code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant updatedAt = Instant.now();
    account.enable(updatedAt);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(code.getExpireAt()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("already enabled.")
        .hasNoCause();
  }

  @Test
  public void test_use_already_used() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), ttl(), this.before);
    final Instant usedAt = this.before.plus(USE_INTERVAL_MIN);
    log.info("GIVEN - usedAt={}", usedAt);

    code.use(usedAt);
    log.info("GIVEN - code={}", code);

    // WHEN
    final ValidationCodeStatusException ex = catchThrowableOfType(() -> code.use(code.getExpireAt()),
        ValidationCodeStatusException.class);
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .hasMessage("invalidated at " + code.getExpireAt())
        .hasNoCause();
  }

  @Test
  public void test_getValidRange() throws Exception {
    // GIVEN
    final ValidationCode validationCode = new ValidationCodeEntity(
        this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    // WHEN
    final Range<Instant> validRange = validationCode.getValidRange();
    log.info("WHEN - validRange={}", validRange);

    // THEN
    assertThat(validRange)
        .isNotNull();
    assertThat(validRange.isInclude(this.before.plus(USE_INTERVAL_MIN).minusNanos(1L)))
        .isFalse();
    assertThat(validRange.isInclude(this.before.plus(USE_INTERVAL_MIN)))
        .isTrue();
    assertThat(validRange.isInclude(this.before.plus(TTL_DEFAULT)))
        .isTrue();
    assertThat(validRange.isInclude(this.before.plus(TTL_DEFAULT).plusNanos(1L)))
        .isFalse();
  }
}
