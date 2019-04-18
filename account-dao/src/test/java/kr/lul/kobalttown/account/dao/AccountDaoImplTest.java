package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.account.AccountDaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.AccountDaoTestConfiguration;

import java.time.Instant;

import static kr.lul.kobalttown.test.account.AccountDomainTestUtil.nickname;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
@Transactional
public class AccountDaoImplTest {
  private static final Logger log = getLogger(AccountDaoImplTest.class);

  @Autowired
  private AccountDao accountDao;

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountDaoTestUtil accountDaoTestUtil;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountDao).isNotNull();
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.accountDaoTestUtil).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
  }

  @Test
  public void test_create_account_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountDao.create((Account) null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("account is null.");
  }

  @Test
  public void test_create_account() throws Exception {
    // Given
    AccountEntity account = this.accountDaoTestUtil.prePersistAccount();
    int id = account.getId();
    String nickname = account.getNickname();
    Instant createdAt = account.getCreatedAt();
    Instant updatedAt = account.getUpdatedAt();
    log.info("GIVEN - account={}", account);

    // When
    Account actual = this.accountDao.create(account);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getNickname, Account::getCreatedAt, Account::getUpdatedAt, Account::getUpdatedAt)
        .containsSequence(nickname, createdAt, updatedAt, createdAt);
    assertThat(actual.getId())
        .isGreaterThan(0)
        .isNotEqualTo(id);
  }

  @Test
  public void test_create_account_with_non_entity() throws Exception {
    assertThatThrownBy(() -> this.accountDao.create(new Account() {
      @Override
      public int getId() {
        return 0;
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public String simpleToString() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }
    }))
        .isInstanceOf(AssertionException.class)
        .hasMessageStartingWith("account is not instance of")
        .hasMessageContaining(AccountEntity.class.getCanonicalName());
  }

  @Test
  public void test_create_account_with_created_account() throws Exception {
    // Given
    Account account = this.accountDaoTestUtil.createdAccount();
    log.info("GIVEN - account={}", account);

    // When & Then
    assertThatThrownBy(() -> this.accountDao.create(account))
        .isNotNull()
        .isInstanceOf(AssertionException.class);
  }

  @Test
  public void test_create_credential_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountDao.create((Credential) null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("credential is null.");
  }

  @Test
  public void test_create_credential() throws Exception {
    // Given
    Account account = this.accountDaoTestUtil.createdAccount();
    CredentialEntity credential = this.accountDaoTestUtil.prePersistCredential((AccountEntity) account);
    long id = credential.getId();
    String publicKey = credential.getPublicKey();
    String secretHash = credential.getSecretHash();
    Instant createdAt = credential.getCreatedAt();
    log.info("GIVEN - account={}, credential={}", account, credential);

    // When
    Credential actual = this.accountDao.create(credential);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(actual)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey, Credential::getSecretHash,
            Credential::getCreatedAt)
        .containsSequence(account, publicKey, secretHash, createdAt);
    assertThat(actual.getId())
        .isGreaterThan(0L)
        .isNotEqualTo(id);
    assertThat(actual.getCreatedAt())
        .isAfterOrEqualTo(this.before);
  }

  @Test
  public void test_create_credential_non_entity() throws Exception {
    assertThatThrownBy(() -> this.accountDao.create(new Credential() {
      @Override
      public long getId() {
        return 0;
      }

      @Override
      public Account getAccount() {
        return null;
      }

      @Override
      public String getPublicKey() {
        return null;
      }

      @Override
      public String getSecretHash() {
        return null;
      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }
    }))
        .isInstanceOf(AssertionException.class)
        .hasMessageStartingWith("credential is not instance of")
        .hasMessageContaining(CredentialEntity.class.getCanonicalName());
  }

  @Test
  public void test_isUsedNickname_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountDao.isUsedNickname(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("nickname is null.");
  }

  @Test
  public void test_isUsedNickname_with_empty() throws Exception {
    assertThatThrownBy(() -> this.accountDao.isUsedNickname(""))
        .isInstanceOf(AssertionException.class)
        .hasMessage("nickname is empty.");
  }

  @Test
  public void test_isUsedNickname_with_used() throws Exception {
    // Given
    Account account = this.accountDaoTestUtil.createdAccount();
    log.info("GIVEN - account={}", account);

    // When & Then
    assertThat(this.accountDao.isUsedNickname(account.getNickname()))
        .isTrue();
  }

  @Test
  public void test_isUsedNickname_with_not_used() throws Exception {
    // Given
    String nickname;
    do {
      nickname = nickname();
    } while (this.accountRepository.existsByNickname(nickname));
    log.info("GIVEN - nickname={}", nickname);

    // When & Then
    assertThat(this.accountDao.isUsedNickname(nickname))
        .isFalse();
  }
}