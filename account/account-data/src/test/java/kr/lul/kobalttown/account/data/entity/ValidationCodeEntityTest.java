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

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
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
}
