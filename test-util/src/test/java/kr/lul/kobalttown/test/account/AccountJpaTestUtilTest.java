package kr.lul.kobalttown.test.account;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.TestUtilTestConfiguration;

import java.time.Instant;

import static java.lang.Thread.sleep;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_REGEX;
import static kr.lul.kobalttown.account.domain.Account.validateNickname;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUtilTestConfiguration.class)
@Transactional
public class AccountJpaTestUtilTest {
  private static final Logger log = getLogger(AccountJpaTestUtilTest.class);

  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountJpaTestUtil).isNotNull();
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();
    assertThat(this.passwordEncoder).isNotNull();

    this.before = this.timeProvider.now();
    sleep(1L);
  }

  @Test
  public void test_prePersistAccount() throws Exception {
    // When
    AccountEntity account = this.accountJpaTestUtil.prePersistAccount();
    log.info("WHEN - account={}", account);

    // Then
    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isLessThanOrEqualTo(0);
    assertThat(account.getNickname())
        .matches(NICKNAME_REGEX);
    validateNickname(account.getNickname());
    assertThat(account.getCreatedAt())
        .isAfter(this.before)
        .isEqualTo(account.getUpdatedAt());
  }

  @Test
  public void test_persistedAccount() throws Exception {
    // When
    AccountEntity account = this.accountJpaTestUtil.persistedAccount();
    log.info("WHEN - account={}", account);

    // Then
    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getNickname())
        .matches(NICKNAME_REGEX);
    validateNickname(account.getNickname());
    assertThat(account.getCreatedAt())
        .isAfter(this.before)
        .isEqualTo(account.getUpdatedAt());
  }

  @Test
  public void test_persistedAccount_with_null_createdAt() throws Exception {
    assertThatThrownBy(() -> this.accountJpaTestUtil.persistedAccount(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("createdAt is null.");
  }

  @Test
  public void test_persistedAccount_with_createdAt() throws Exception {
    // Given
    Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - createdAt={}", createdAt);

    // When
    AccountEntity account = this.accountJpaTestUtil.persistedAccount(createdAt);
    log.info("WHEN - account={}", account);

    // Then
    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getNickname())
        .matches(NICKNAME_REGEX);
    validateNickname(account.getNickname());
    assertThat(account.getCreatedAt())
        .isEqualTo(createdAt)
        .isEqualTo(account.getUpdatedAt());
  }

  @Test
  public void test_prePersistCredential_with_null_account() throws Exception {
    assertThatThrownBy(() -> this.accountJpaTestUtil.prePersistCredential(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("account is null.");
  }

  @Test
  public void test_prePersistCredential_with_pre_persist_account() throws Exception {
    // Given
    AccountEntity account = this.accountJpaTestUtil.prePersistAccount();
    log.info("GIVEN - account={}", account);

    // When
    CredentialEntity credential = this.accountJpaTestUtil.prePersistCredential(account);
    log.info("WHEN - credential={}", credential);

    // Then
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey)
        .containsSequence(account, account.getNickname());
    assertThat(credential.getId())
        .isLessThanOrEqualTo(0L);
    assertThat(this.passwordEncoder.matches(AccountJpaTestUtil.DEFAULT_PASSWORD, credential.getSecretHash()))
        .isTrue();
    assertThat(credential.getCreatedAt())
        .isAfter(this.before);
  }

  @Test
  public void test_persistedCredential() throws Exception {
    // When
    CredentialEntity credential = this.accountJpaTestUtil.persistedCredential();
    Account account = credential.getAccount();
    log.info("WHEN - credential={}", credential);

    // Then
    assertThat(credential)
        .isNotNull();
    assertThat(credential.getId())
        .isGreaterThan(0L);
    assertThat(credential.getPublicKey())
        .isEqualTo(account.getNickname());
    assertThat(this.passwordEncoder.matches(AccountJpaTestUtil.DEFAULT_PASSWORD, credential.getSecretHash()))
        .isTrue();
    assertThat(credential.getCreatedAt())
        .isAfter(this.before)
        .isAfterOrEqualTo(account.getCreatedAt());

    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getNickname())
        .matches(NICKNAME_REGEX);
    validateNickname(account.getNickname());
    assertThat(account.getCreatedAt())
        .isAfter(this.before)
        .isEqualTo(account.getUpdatedAt());
  }

  @Test
  public void test_persistedCredential_with_null_account() throws Exception {
    assertThatThrownBy(() -> this.accountJpaTestUtil.persistedCredential(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("account is null.");
  }

  @Test
  public void test_persistedCredential_with_pre_persisted_account() throws Exception {
    // Given
    AccountEntity account = this.accountJpaTestUtil.prePersistAccount();
    log.info("GIVEN - account={}", account);

    // When & Then
    assertThatThrownBy(() -> this.accountJpaTestUtil.persistedCredential(account))
        .isInstanceOf(AssertionException.class)
        .hasMessageStartingWith("account.id is not positive");
  }

  @Test
  public void test_persistedCredential_with_persisted_account() throws Exception {
    // Given
    AccountEntity account = this.accountJpaTestUtil.persistedAccount();
    log.info("GIVEN - account={}", account);

    // When
    CredentialEntity credential = this.accountJpaTestUtil.persistedCredential(account);
    log.info("WHEN - credential={}", credential);

    // Then
    assertThat(credential)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey)
        .containsSequence(account, account.getNickname());
    assertThat(credential.getId())
        .isGreaterThan(0L);
    assertThat(credential.getCreatedAt())
        .isAfter(this.before)
        .isAfterOrEqualTo(account.getCreatedAt());
  }

  @Test
  @Repeat(1000)
  public void test_unusedNickname() throws Exception {
    // When
    String nickname = this.accountJpaTestUtil.unusedNickname();
    log.info("WHEN - nickname={}", nickname);

    // Then
    assertThat(this.accountRepository.existsByNickname(nickname))
        .isFalse();
  }
}