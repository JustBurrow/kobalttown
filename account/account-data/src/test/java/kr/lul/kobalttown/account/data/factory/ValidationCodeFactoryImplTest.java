package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.ValidationCode.Status.*;
import static kr.lul.kobalttown.account.domain.ValidationCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.ValidationCode.TTL_MIN;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class ValidationCodeFactoryImplTest {
  private static final Logger log = getLogger(ValidationCodeFactoryImplTest.class);

  private ValidationCodeFactory factory;
  private Instant before;

  private AccountFactory accountFactory;

  @Before
  public void setUp() throws Exception {
    this.factory = new ValidationCodeFactoryImpl();
    this.before = Instant.now();

    this.accountFactory = new AccountFactoryImpl();
  }

  @Test
  public void test_create_with_null_context_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_defaults() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, email(), code(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, code(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_create_with_empty_email_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", code(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_create_with_null_code_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_code_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_createdAt_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), null))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_create_with_defaults() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);

    // WHEN
    final ValidationCode validationCode = this.factory.create(new Context(), account, email, code, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            code, this.before.plus(TTL_DEFAULT), ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), ttl(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_ttl() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, email(), code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("email is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_email_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("email is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_code_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), (Duration) null, this.before))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_create_with_0_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), Duration.ZERO, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_create_with_ttl() throws Exception {
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
    final ValidationCode validationCode = this.factory.create(new Context(), account, email, code, ttl, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            code, this.before.plus(ttl), ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(), null, email(), code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_email_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_code_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), (Instant) null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_too_early_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), this.before, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_create_with_expireAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), this.before.plus(TTL_DEFAULT), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_expireAt() throws Exception {
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
    final ValidationCode validationCode = this.factory
                                              .create(new Context(), account, email, code, expireAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            code, expireAt, ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_for_test_with_null_account() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L, null, email(), code(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("account is null");
  }

  @Test
  public void test_create_for_test_with_null_email() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, code(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_create_for_test_with_empty_email() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", code(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_create_for_test_with_null_code() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.");
  }

  @Test
  public void test_create_for_test_with_empty_code() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.");
  }

  @Test
  public void test_create_for_test_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), code(), null, null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.");
  }

  @Test
  public void test_create_for_test_with_too_early_expireAt() throws Exception {
    // WHEN
    final ValidationException ex = catchThrowableOfType(() -> this.factory.create(1L,
        this.accountFactory.create(1L, nickname(), false, this.before),
        email(), code(), this.before.plus(TTL_MIN).minusNanos(1L), null, null, this.before),
        ValidationException.class);
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range");
    ;
  }

  @Test
  public void test_create_for_test_with_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), null, null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_create_for_test() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final ValidationCode validationCode =
        this.factory.create(1L, account, email, code, expireAt, null, null, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            code, expireAt, ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_for_test_with_USED() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant statusAt = expireAt.minusNanos(1L);
    log.info("GIVEN - statusAt={}", statusAt);

    // WHEN
    final ValidationCode validationCode = this.factory.create(1L, account, email, code, expireAt, USED, statusAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            code, expireAt, USED, statusAt,
            true, false, this.before, statusAt);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, statusAt);
  }

  @Test
  public void test_create_for_test_with_EXPIRED() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant expiredAt = expireAt.plusMillis(1L);
    log.info("GIVEN - expiredAt={}", expiredAt);

    // WHEN
    final ValidationCode validationCode =
        this.factory.create(1L, account, email, code, expireAt, EXPIRED, expiredAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            code, expireAt, EXPIRED, expiredAt,
            false, true, this.before, expiredAt);
  }
}
