package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class CredentialFactoryImplTest {
  private static final Logger log = getLogger(CredentialFactoryImplTest.class);

  private CredentialFactory factory;
  private Instant before;

  private AccountFactory accountFactory;
  private PasswordEncoder passwordEncoder;

  @Before
  public void setUp() throws Exception {
    this.factory = new CredentialFactoryImpl();
    this.before = Instant.now();
    this.accountFactory = new AccountFactoryImpl();
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  @Test
  public void test_create_with_null_context_and_account_and_userKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(null,
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), this.passwordEncoder.encode("password"), this.before))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.");
  }

  @Test
  public void test_create_with_context_and_null_account_and_userKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(), null, userKey(), this.passwordEncoder.encode("password"), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("account is null.");
  }

  @Test
  public void test_create_with_context_and_account_and_null_publicKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.passwordEncoder.encode("password"), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("publicKey is null.");
  }

  @Test
  public void test_create_with_context_and_account_and_empty_publicKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", this.passwordEncoder.encode("password"), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("publicKey is empty.");
  }

  @Test
  public void test_create_with_context_and_account_and_publicKey_and_null_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("secretHash is null.");
  }

  @Test
  public void test_create_with_context_and_account_and_publicKey_and_empty_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), "", this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("secretHash is empty.");
  }

  @Test
  public void test_create_with_context_and_account_and_publicKey_and_secretHash_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(new Context(),
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), this.passwordEncoder.encode("password"), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_create_with_context_and_account_and_publicKey_and_secretHash_and_createdAt() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String userKey = userKey();
    log.info("GIVEN - userKey={}", userKey);
    final String secretHash = this.passwordEncoder.encode("password");
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN
    final Credential credential = this.factory.create(new Context(), account, userKey, secretHash, this.before);
    log.info("WHEN - credential={}", credential);

    // THEN
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getId, Credential::getAccount, Credential::getPublicKey, Credential::getSecretHash,
            Creatable::getCreatedAt)
        .containsSequence(0L, account, userKey, secretHash, this.before);
  }

  @Test
  public void test_create_with_id_and_null_account_and_publicKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(() -> this.factory.create(1L, null, userKey(), this.passwordEncoder.encode(""), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("account is null");
  }

  @Test
  public void test_create_with_id_and_account_and_null_publicKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            null, this.passwordEncoder.encode(""), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("publicKey is null.");
  }

  @Test
  public void test_create_with_id_and_account_and_empty_publicKey_and_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            "", this.passwordEncoder.encode(""), this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("publicKey is empty.");
  }

  @Test
  public void test_create_with_id_and_account_and_publicKey_and_null_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), null, this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("secretHash is null.");
  }

  @Test
  public void test_create_with_id_and_account_and_publicKey_and_empty_secretHash_and_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), "", this.before))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("secretHash is empty.");
  }

  @Test
  public void test_create_with_id_and_account_and_publicKey_and_secretHash_and_null_createdAt() throws Exception {
    assertThatThrownBy(
        () -> this.factory.create(1L,
            this.accountFactory.create(1L, nickname(), false, this.before),
            userKey(), this.passwordEncoder.encode(""), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("createdAt is null.");
  }

  @Test
  public void test_create_with_id_and_account_and_publicKey_and_secretHash_and_createdAt() throws Exception {
    // GIVEN
    final long id = current().nextLong(Long.MAX_VALUE) + 1L;
    log.info("GIVEN - id={}", id);
    final Account account = this.accountFactory.create(1L, nickname(), false, this.before);
    log.info("GIVEN - account={}", account);
    final String userKey = userKey();
    log.info("GIVEN - userKey={}", userKey);
    final String secretHash = this.passwordEncoder.encode("password");
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN
    final Credential credential = this.factory.create(id, account, userKey, secretHash, this.before);
    log.info("WHEN - credential={}", credential);

    // THEN
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getId, Credential::getAccount)
        .containsSequence(id, account);
  }
}
