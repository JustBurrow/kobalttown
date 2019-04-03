package kr.lul.kobalttown.account.jpa.repository;

import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.AccountJpaTestConfiguration;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.test.account.jpa.AccountJpaTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AccountJpaTestConfiguration.class)
@DataJpaTest
public class CredentialRepositoryTest {
  private static final Logger log = getLogger(CredentialRepositoryTest.class);

  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;
  @Autowired
  private AccountRepository accountRepository;

  @Before
  public void setUp() throws Exception {
    assertThat(this.credentialRepository).isNotNull();
    assertThat(this.accountJpaTestUtil).isNotNull();
    assertThat(this.accountRepository).isNotNull();
  }

  @Test
  public void test_findAll() throws Exception {
    // When
    List<CredentialEntity> list = this.credentialRepository.findAll();
    log.info("WHEN - list={}", list);

    // Then
    assertThat(list)
        .isNotNull()
        .isEmpty();
  }

  @Test
  public void test_save_with_null() throws Exception {
    assertThatThrownBy(() -> this.credentialRepository.save(null))
        .isNotNull();
  }

  @Test
  public void test_save() throws Exception {
    // Given
    AccountEntity account = this.accountRepository.save(this.accountJpaTestUtil.prePersistAccount());
    CredentialEntity credential = this.accountJpaTestUtil.prePersistCredential(account);
    log.info("GIVEN - credential={}", credential);
    long id = credential.getId();
    String publicKey = credential.getPublicKey();
    String secretHash = credential.getSecretHash();
    Instant createdAt = credential.getCreatedAt();
    log.info("GIVEN - account={}, publicKey={}, secretHash={}, createdAt={}", account, publicKey, secretHash,
        createdAt);

    // When
    CredentialEntity actual = this.credentialRepository.save(credential);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(actual)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey, Credential::getSecretHash,
            Credential::getCreatedAt)
        .containsSequence(account, publicKey, secretHash, createdAt);
    assertThat(actual.getId())
        .isNotEqualTo(id)
        .isGreaterThan(0L);
  }
}