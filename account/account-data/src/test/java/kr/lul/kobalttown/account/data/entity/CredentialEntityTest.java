package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.AccountFactoryImpl;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Credential.PUBLIC_KEY_MAX_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class CredentialEntityTest {
  private static final Logger log = getLogger(CredentialEntityTest.class);

  private AccountFactory accountFactory;
  private PasswordEncoder passwordEncoder;
  private Instant createdAt;

  @Before
  public void setUp() throws Exception {
    this.accountFactory = new AccountFactoryImpl();
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.createdAt = Instant.now();
  }

  @Test
  public void test_new_with_null_account() throws Exception {
    // GIVEN
    final String publicKey = "just.burrow." + current().nextInt(Integer.MAX_VALUE) + "@lul.kr";
    log.info("GIVEN - publicKey={}", publicKey);
    final String secretHash = this.passwordEncoder.encode("password");
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(null, publicKey, secretHash, this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessage("account is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_not_persisted_account() throws Exception {
    // GIVEN
    final Account account = new AccountEntity();
    log.info("GIVEN - account={}", account);
    final String publicKey = "just.burrow." + current().nextInt(Integer.MAX_VALUE) + "@lul.kr";
    log.info("GIVEN - publicKey={}", publicKey);
    final String secretHash = this.passwordEncoder.encode("password");
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, publicKey, secretHash, this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("illegal account id")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_publicKey() throws Exception {
    // GIVEN
    final long id = current().nextLong(1, Long.MAX_VALUE);
    log.info("GIVEN - id={}", id);
    final Account account = this.accountFactory
                                .create(id, "just burrow #" + current().nextInt(Integer.MAX_VALUE), true,
                                    this.createdAt);
    log.info("GIVEN - account={}", account);
    assertThat(account.getId())
        .isEqualTo(id);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, null, this.passwordEncoder.encode("password"),
        this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessage("publicKey is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_publicKey() throws Exception {
    // GIVEN
    final long id = current().nextLong(1, Long.MAX_VALUE);
    log.info("GIVEN - id={}", id);
    final Account account = this.accountFactory
                                .create(id, "just burrow #" + current().nextInt(Integer.MAX_VALUE), true,
                                    this.createdAt);
    log.info("GIVEN - account={}", account);
    assertThat(account.getId())
        .isEqualTo(id);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, "", this.passwordEncoder.encode("password"), this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessage("publicKey is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_short_publicKey() throws Exception {
    // GIVEN
    final long id = current().nextLong(1, Long.MAX_VALUE);
    log.info("GIVEN - id={}", id);
    final Account account = this.accountFactory
                                .create(id, "just burrow #" + current().nextInt(Integer.MAX_VALUE), true,
                                    this.createdAt);
    log.info("GIVEN - account={}", account);
    assertThat(account.getId())
        .isEqualTo(id);

    final String publicKey = random(Credential.PUBLIC_KEY_MIN_LENGTH - 1);
    log.info("GIVEN - publicKey={}", publicKey);

    // WHEN & THEN
    assertThatThrownBy(() ->
                           new CredentialEntity(account, publicKey, this.passwordEncoder.encode("password"),
                               this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too short publicKey")
        .hasNoCause();
  }

  @Test
  public void test_new_with_long_publicKey() throws Exception {
    // GIVEN
    final long id = current().nextLong(1, Long.MAX_VALUE);
    log.info("GIVEN - id={}", id);
    final Account account = this.accountFactory
                                .create(id, "just burrow #" + current().nextInt(Integer.MAX_VALUE), true,
                                    this.createdAt);
    log.info("GIVEN - account={}", account);
    assertThat(account.getId())
        .isEqualTo(id);

    final String publicKey = random(PUBLIC_KEY_MAX_LENGTH + 1);
    log.info("GIVEN - publicKey={}", publicKey);

    // WHEN & THEN
    assertThatThrownBy(() ->
                           new CredentialEntity(account, publicKey, this.passwordEncoder.encode("password"),
                               this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("too long publicKey")
        .hasNoCause();
  }

  @Test
  public void test_new_with_null_secretHash() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(
        1L, "Just Burrow #" + current().nextInt(Integer.MAX_VALUE), true, this.createdAt);
    log.info("GIVEN - account={}", account);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, "just.burrow@lul.kr", null, this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessage("secretHash is null.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_empty_secretHash() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(
        1L, "Just Burrow #" + current().nextInt(Integer.MAX_VALUE), true, this.createdAt);
    log.info("GIVEN - account={}", account);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, "just.burrow@lul.kr", "", this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessage("secretHash is empty.")
        .hasNoCause();
  }

  @Test
  public void test_new_with_illegal_pattern_secretHash() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(
        1L, "Just Burrow #" + current().nextInt(Integer.MAX_VALUE), true, this.createdAt);
    log.info("GIVEN - account={}", account);

    final String secretHash = random(10);
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN & THEN
    assertThatThrownBy(() -> new CredentialEntity(account, "just.burrow@lul.kr", secretHash, this.createdAt))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("illegal secretHash pattern");
  }

  @Test
  public void test_new() throws Exception {
    // GIVEN
    final Account account = this.accountFactory.create(current().nextLong(Long.MAX_VALUE) + 1L,
        "Just Burrow #" + current().nextInt(Integer.MAX_VALUE), true, this.createdAt);
    log.info("GIVEN - account={}", account);
    final String secretHash = this.passwordEncoder.encode("password");
    log.info("GIVEN - secretHash={}", secretHash);

    // WHEN
    final Credential credential = new CredentialEntity(account, "just.burrow@lul.kr", secretHash, this.createdAt);
    log.info("WHEN - credential={}", credential);

    // THEN
    assertThat(credential)
        .extracting(Credential::getAccount, Credential::getPublicKey, Credential::getSecretHash,
            Credential::getCreatedAt)
        .containsSequence(account, "just.burrow@lul.kr", secretHash, this.createdAt);
  }
}
