package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.AccountFactoryImpl;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static java.time.temporal.ChronoUnit.NANOS;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.ValidationCode.MIN_USE_INTERVAL;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    this.before = Instant.now();
  }

  @Test
  public void test_new_with_null_account_and_expireAt() throws Exception {
    assertThatThrownBy(() -> new ValidationCodeEntity(null, code(), this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_account_and_ttl() throws Exception {
    assertThatThrownBy(() -> new ValidationCodeEntity(null, code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_enabled_account_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), true, this.before), code(),
            this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_enabled_account_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), true, this.before), code(),
            ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), null,
            this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), null, ttl(),
            this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), "",
            this.before.plus(ttl()), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), "", ttl(),
            this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), code(),
            (Instant) null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_expireAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), code(),
            this.before.plus(ttl()), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_ttl_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before), code(), ttl(),
            null))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_new_with_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(ttl());
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getCode,
            ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, code, expireAt,
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_new_with_ttl() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Duration ttl = ttl();
    log.info("GIVEN - ttl={}", ttl);

    // WHEN
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, ttl, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getCode,
            ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, code, this.before.plus(ttl),
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_isValid_with_null() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.isValid(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("when is null.")
        .hasNoCause();
  }

  @Test
  public void test_isValid_with_before_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(MIN_USE_INTERVAL).minusNanos(1L)))
        .isFalse();
  }

  @Test
  public void test_isValid_with_min_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(MIN_USE_INTERVAL)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_after_min_validRange() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getCreatedAt().plus(MIN_USE_INTERVAL).plusNanos(1L)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt()))
        .isTrue();
  }

  @Test
  public void test_isValid_with_before_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt().minusNanos(1L)))
        .isTrue();
  }

  @Test
  public void test_isValid_with_after_expireAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt().plusNanos(1L)))
        .isFalse();
  }

  @Test
  public void test_isValid_used() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    code.use(code.getExpireAt());
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt()))
        .isFalse();
  }

  @Test
  public void test_isValid_expired() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    code.expire(code.getExpireAt().plusNanos(1L));
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThat(code.isValid(code.getExpireAt()))
        .isFalse();
  }

  @Test
  public void test_use_with_null() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("when is null.")
        .hasNoCause();
  }

  @Test
  public void test_use_with_before_createdAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant when = this.before.minus(1L, NANOS);
    log.info("GIVEN - when={}", when);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(when))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("when=" + when)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_after_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    final Instant when = expireAt.plus(1L, NANOS);
    log.info("GIVEN - when={}", when);

    // WHEN & THEN
    assertThatThrownBy(() -> validationCode.use(when))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too late use")
        .hasMessageContaining("when=" + when)
        .hasMessageContaining("validRange=" + validationCode.getValidRange())
        .hasNoCause();
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Updatable::getUpdatedAt)
        .containsSequence(account, code, expireAt,
            false, null, true, when,
            when);
    assertThat(account)
        .extracting(Account::isEnabled, Updatable::getUpdatedAt)
        .containsSequence(false, this.before);
  }

  @Test
  public void test_use_with_createdAt() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("when=" + this.before)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_before_min_interval() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant when = this.before.plus(MIN_USE_INTERVAL).minus(1L, NANOS);
    log.info("GIVEN - when={}", when);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(when))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early use")
        .hasMessageContaining("when=" + when)
        .hasMessageContaining("validRange=" + code.getValidRange())
        .hasNoCause();
  }

  @Test
  public void test_use_with_min_interval() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    final Instant when = this.before.plus(MIN_USE_INTERVAL);
    log.info("GIVEN - when={}", when);

    // WHEN
    validationCode.use(when);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, code, expireAt,
            true, when, false, null,
            this.before, when);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, when);
  }

  @Test
  public void test_use_with_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    // WHEN
    validationCode.use(expireAt);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getAccount, ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, code, expireAt,
            true, expireAt, false, null,
            this.before, expireAt);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, expireAt);
  }

  @Test
  public void test_use_with_enabled_account() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final ValidationCode code = new ValidationCodeEntity(account, code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    final Instant updatedAt = Instant.now();
    account.enable(updatedAt);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(code.getExpireAt()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("already enabled account.")
        .hasNoCause();
  }

  @Test
  public void test_use_already_used() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    final Instant usedAt = this.before.plus(MIN_USE_INTERVAL);
    log.info("GIVEN - usedAt={}", usedAt);

    code.use(usedAt);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.use(code.getExpireAt()))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("already used")
        .hasMessageContaining("usedAt=" + usedAt)
        .hasNoCause();
  }

  @Test
  public void test_expire_with_null() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.expire(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("when is null.")
        .hasNoCause();
  }

  @Test
  public void test_expire_with_before_expireAt() throws Exception {
    // GIVEN
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), expireAt, this.before);
    log.info("GIVEN - code={}", code);

    final Instant when = expireAt.minusNanos(1L);
    log.info("GIVEN - when={}", when);

    // WHEN & THEN
    assertThatThrownBy(() -> code.expire(when))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early expire")
        .hasMessageContaining("when=" + when)
        .hasMessageContaining("expireAt=" + expireAt)
        .hasNoCause();
  }

  @Test
  public void test_expire_with_expireAt() throws Exception {
    // GIVEN
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), expireAt, this.before);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.expire(expireAt))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("too early expire")
        .hasMessageContaining("when=" + expireAt)
        .hasMessageContaining("expireAt=" + expireAt)
        .hasNoCause();
  }

  @Test
  public void test_expire_already_used() throws Exception {
    // GIVEN
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), expireAt, this.before);
    final Instant usedAt = this.before.plus(MIN_USE_INTERVAL);
    log.info("GIVEN - usedAt={}", usedAt);

    code.use(usedAt);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.expire(expireAt.plusNanos(1L)))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("already used")
        .hasMessageContaining("usedAt=" + usedAt)
        .hasNoCause();
  }

  @Test
  public void test_expire_already_expired() throws Exception {
    // GIVEN
    final ValidationCode code = new ValidationCodeEntity(this.accountFactory.create(1L, nickname(), false, this.before),
        code(), ttl(), this.before);
    final Instant expiredAt = code.getExpireAt().plusNanos(1L);
    code.expire(expiredAt);
    log.info("GIVEN - code={}", code);

    // WHEN & THEN
    assertThatThrownBy(() -> code.expire(expiredAt.plusNanos(2L)))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageStartingWith("already expired")
        .hasMessageContaining("expiredAt=" + expiredAt)
        .hasNoCause();
  }

  @Test
  public void test_expire() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    final String code = code();
    final Instant expireAt = this.before.plus(ttl());
    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    final Instant when = expireAt.plusNanos(1L);
    log.info("GIVEN - when={}", when);

    // WHEN
    validationCode.expire(when);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .extracting(ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(code, expireAt,
            false, null, true, when,
            this.before, when);
    assertThat(validationCode.getAccount())
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(false, this.before, this.before);
  }
}
