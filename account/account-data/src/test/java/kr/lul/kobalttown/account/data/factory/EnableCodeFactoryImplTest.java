package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.EnableCode.Status.*;
import static kr.lul.kobalttown.account.domain.EnableCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.EnableCode.TTL_MIN;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.token;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.ttl;
import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class EnableCodeFactoryImplTest {
  private static final Logger log = getLogger(EnableCodeFactoryImplTest.class);

  private EnableCodeFactory factory;
  private Instant before;

  private AccountFactory accountFactory;

  @Before
  public void setUp() throws Exception {
    this.factory = new EnableCodeFactoryImpl();
    this.before = Instant.now();

    this.accountFactory = new AccountFactoryImpl();
  }

  @Test
  public void test_create_with_null_context_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_defaults() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, email(), token(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, token(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_create_with_empty_email_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", token(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_create_with_null_token_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_token_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_createdAt_and_defaults() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), null))
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
    final String token = token();
    log.info("GIVEN - token={}", token);

    // WHEN
    final EnableCode code = this.factory.create(new Context(), account, email, token, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            token, this.before.plus(TTL_DEFAULT), ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), ttl(), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_ttl() throws Exception {
    assertThatThrownBy(() -> this.factory.create(new Context(), null, email(), token(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, token(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("email is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_email_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", token(), ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("email is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_token_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("token is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_token_and_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", ttl(), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("token is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), (Duration) null, this.before))
        .isInstanceOf(NullPointerException.class)
        .hasNoCause();
  }

  @Test
  public void test_create_with_0_ttl() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), Duration.ZERO, this.before))
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
    final String token = token();
    log.info("GIVEN - token={}", token);
    final Duration ttl = ttl();
    log.info("GIVEN - ttl={}", ttl);

    // WHEN
    final EnableCode code = this.factory.create(new Context(), account, email, token, ttl, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            token, this.before.plus(ttl), ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_with_null_context_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_account_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(), null, email(), token(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_email_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, token(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_email_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", token(), this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_token_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_empty_token_and_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before.plus(TTL_DEFAULT), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is empty.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), (Instant) null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_too_early_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), this.before, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("ttl is out of range")
        .hasNoCause();
  }

  @Test
  public void test_create_with_expireAt_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), this.before.plus(TTL_DEFAULT), null))
        .isInstanceOf(ValidationException.class)
        .hasNoCause()
        .extracting("targetName", "target", "message")
        .containsSequence("createdAt", null, "createdAt is null.");
  }

  @Test
  public void test_create_with_expireAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String token = token();
    log.info("GIVEN - token={}", token);
    final Instant expireAt = this.before.plus(ttl());
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final EnableCode code = this.factory.create(new Context(), account, email, token, expireAt, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(0L, account, email,
            token, expireAt, ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_for_test_with_null_account() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L, null, email(), token(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("account is null");
  }

  @Test
  public void test_create_for_test_with_null_email() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, token(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_create_for_test_with_empty_email() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", token(), this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_create_for_test_with_null_token() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is null.");
  }

  @Test
  public void test_create_for_test_with_empty_token() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), "", this.before.plus(TTL_DEFAULT), null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("token is empty.");
  }

  @Test
  public void test_create_for_test_with_null_expireAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), null, null, null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessage("expireAt is null.");
  }

  @Test
  public void test_create_for_test_with_too_early_expireAt() throws Exception {
    // WHEN
    final ValidationException ex = catchThrowableOfType(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), token(), this.before.plus(TTL_MIN).minusNanos(1L), null, null, this.before),
        ValidationException.class);
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("ttl is out of range");
  }

  @Test
  public void test_create_for_test_with_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            email(), null, this.before.plus(TTL_DEFAULT), null, null, null))
        .isInstanceOf(ValidationException.class)
        .hasNoCause()
        .extracting("targetName", "target", "message")
        .containsSequence("createdAt", null, "createdAt is null.");
  }

  @Test
  public void test_create_for_test() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String token = token();
    log.info("GIVEN - token={}", token);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);

    // WHEN
    final EnableCode code = this.factory.create(1L, account, email, token, expireAt, null, null, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            token, expireAt, ISSUED, this.before,
            false, false, this.before, this.before);
  }

  @Test
  public void test_create_for_test_with_USED() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String email = email();
    log.info("GIVEN - email={}", email);
    final String token = token();
    log.info("GIVEN - token={}", token);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant statusAt = expireAt.minusNanos(1L);
    log.info("GIVEN - statusAt={}", statusAt);

    // WHEN
    final EnableCode code = this.factory.create(1L, account, email, token, expireAt, USED, statusAt, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            token, expireAt, USED, statusAt,
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
    final String token = token();
    log.info("GIVEN - token={}", token);
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - expireAt={}", expireAt);
    final Instant expiredAt = expireAt.plusMillis(1L);
    log.info("GIVEN - expiredAt={}", expiredAt);

    // WHEN
    final EnableCode code = this.factory.create(1L, account, email, token, expireAt, EXPIRED, expiredAt, this.before);
    log.info("WHEN - code={}", code);

    // THEN
    assertThat(code)
        .isNotNull()
        .extracting(EnableCode::getId, EnableCode::getAccount, EnableCode::getEmail,
            EnableCode::getToken, EnableCode::getExpireAt, EnableCode::getStatus, EnableCode::getStatusAt,
            EnableCode::isUsed, EnableCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(1L, account, email,
            token, expireAt, EXPIRED, expiredAt,
            false, true, this.before, expiredAt);
  }
}
