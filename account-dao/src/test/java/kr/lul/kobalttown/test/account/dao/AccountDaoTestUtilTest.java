package kr.lul.kobalttown.test.account.dao;

import kr.lul.kobalttown.account.dao.AccountDaoTestConfiguration;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static kr.lul.kobalttown.account.domain.Account.NICKNAME_REGEX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
@Transactional
public class AccountDaoTestUtilTest {
  private static final Logger log = getLogger(AccountDaoTestUtilTest.class);

  @Autowired
  private AccountDaoTestUtil accountDaoTestUtil;

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountDaoTestUtil).isNotNull();
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.credentialRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
  }

  @Test
  public void test_createdAccount() throws Exception {
    // When
    Account account = this.accountDaoTestUtil.createdAccount();
    log.info("WHEN - account={}", account);

    // Then
    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getNickname())
        .isNotEmpty()
        .matches(NICKNAME_REGEX);
    assertThat(account.getCreatedAt())
        .isNotNull()
        .isAfterOrEqualTo(this.before)
        .isEqualTo(account.getUpdatedAt());
  }

  @Test
  public void test_createdCredential() throws Exception {
    // When
    Credential credential = this.accountDaoTestUtil.createdCredential();
    log.info("WHEN - credential={}", credential);

    // Then
    assertThat(credential)
        .isNotNull();
    assertThat(credential.getId())
        .isGreaterThan(0L);
    assertThat(credential.getAccount())
        .isNotNull();
    assertThat(credential.getCreatedAt())
        .isAfterOrEqualTo(this.before)
        .isAfterOrEqualTo(credential.getAccount().getCreatedAt());
  }
}