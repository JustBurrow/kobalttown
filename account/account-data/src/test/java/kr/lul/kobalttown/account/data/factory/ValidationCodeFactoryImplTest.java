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
import static kr.lul.kobalttown.account.domain.ValidationCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
  public void test_create_with_null_context_and_account_and_code_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_null_account_code_and_createdAt() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, code(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_null_code_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_empty_code_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), null))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);

    // WHEN
    final ValidationCode validationCode = this.factory.create(new Context(), account, code, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account,
            code, this.before.plus(TTL_DEFAULT),
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_account_and_code_and_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), ttl(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_null_account_and_code_and_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, code(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_null_code_and_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_empty_code_and_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_null_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), (Duration) null, this.before))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_0_ttl_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), Duration.ZERO, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_ttl_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Duration ttl = ttl();
    log.info("GIVEN - ttl={}", ttl);

    // WHEN
    final ValidationCode validationCode = this.factory.create(new Context(), account, code, ttl, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account,
            code, this.before.plus(ttl),
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_account_and_code_and_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_null_account_and_code_and_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(), null, code(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_null_code_and_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_empty_code_and_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_null_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), (Instant) null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_createdAt_expireAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), this.before, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_expireAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), this.before.plus(TTL_DEFAULT), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_context_and_account_and_code_and_expireAt_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(ttl());
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final ValidationCode validationCode = this.factory.create(new Context(), account, code, expireAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account,
            code, expireAt,
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_create_id_and_null_account_and_code_and_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L, null, code(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("account is null");
  }

  @Test
  public void test_create_id_and_account_and_null_code_and_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is null.");
  }

  @Test
  public void test_create_id_and_account_and_empty_code_and_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("code is empty.");
  }

  @Test
  public void test_create_id_and_account_and_code_and_null_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            code(), null, null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.");
  }

  @Test
  public void test_create_id_and_account_and_code_and_createdAt_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.before, null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("code is null.");
  }

  @Test
  public void test_create_id_and_account_and_code_and_expireAt_and_null_usedAt_and_null_expiredAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.before.plus(TTL_DEFAULT), null, null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_create_id_and_account_and_code_and_expireAt_and_null_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final ValidationCode validationCode = this.factory.create(1L, account, code, expireAt, null, null, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account,
            code, expireAt,
            false, null, false, null,
            this.before, this.before);
  }

  @Test
  public void test_create_id_and_account_and_code_and_expireAt_and_usedAt_and_null_expiredAt_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant usedAt = expireAt.minusNanos(1L);
    log.info("GIVEN - usedAt={}", usedAt);

    // WHEN
    final ValidationCode validationCode = this.factory.create(1L, account, code, expireAt, usedAt, null, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account,
            code, expireAt,
            true, usedAt, false, null,
            this.before, usedAt);
    assertThat(account)
        .extracting(Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(true, this.before, usedAt);
  }

  @Test
  public void test_create_id_and_account_and_code_and_expireAt_and_null_usedAt_and_expiredAt_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String code = code();
    log.info("GIVEN - code={}", code);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant expiredAt = expireAt.plusNanos(1L);
    log.info("GIVEN - expiredAt={}", expiredAt);

    // WHEN
    final ValidationCode validationCode = this.factory
                                              .create(1L, account, code, expireAt, null, expiredAt, this.before);
    log.info("WHEN - validationCode={}", validationCode);

    // THEN
    assertThat(validationCode)
        .isNotNull()
        .extracting(ValidationCode::getId, ValidationCode::getAccount,
            ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account,
            code, expireAt,
            false, null, true, expiredAt,
            this.before, expiredAt);
  }
}
