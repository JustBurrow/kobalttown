package kr.lul.kobalttown.test.account;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.TestUtilTestConfiguration;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-05-03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestUtilTestConfiguration.class)
@Transactional
public class AccountServiceTestUtilTest {
  private static final Logger log = getLogger(AccountServiceTestUtilTest.class);

  @Autowired
  private AccountServiceTestUtil testUtil;

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private PasswordEncoder passwordEncoder;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.testUtil).isNotNull();
    assertThat(this.accountService).isNotNull();
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.credentialRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();
    assertThat(this.passwordEncoder).isNotNull();

    this.before = this.timeProvider.now();
  }

  @Test
  public void test_createdAccount() throws Exception {
    // When
    Account account = this.testUtil.createdAccount();
    String nickname = account.getNickname();
    this.accountRepository.flush();
    this.credentialRepository.flush();
    log.info("WHEN - account={}", account);

    // Then
    assertThat(account).isNotNull();
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getCreatedAt())
        .isNotNull()
        .isEqualTo(account.getUpdatedAt())
        .isAfter(this.before);

    List<CredentialEntity> credentials = this.credentialRepository.findAllByAccount((AccountEntity) account);
    assertThat(credentials)
        .hasSize(1);
    assertThat(credentials.get(0))
        .isNotNull()
        .extracting(CredentialEntity::getAccount, CredentialEntity::getPublicKey)
        .containsSequence(account, nickname);
    assertThat(this.passwordEncoder.matches(AccountJpaTestUtil.DEFAULT_PASSWORD, credentials.get(0).getSecretHash()))
        .isTrue();
  }
}