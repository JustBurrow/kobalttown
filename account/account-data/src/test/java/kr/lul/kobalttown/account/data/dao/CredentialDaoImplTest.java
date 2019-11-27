package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Creatable;
import kr.lul.common.data.UuidContext;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.data.repository.AccountRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
@Transactional
public class CredentialDaoImplTest {
  private static final Logger log = getLogger(CredentialDaoImplTest.class);

  @Autowired
  private CredentialDao dao;
  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private SecurityEncoder securityEncoder;
  @Autowired
  private EntityManager entityManager;

  private Instant instant;

  @Before
  public void setUp() throws Exception {
    assertThat(this.dao).isNotNull();
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();
    Assertions.assertThat(this.securityEncoder).isNotNull();
    assertThat(this.entityManager).isNotNull();

    this.instant = this.timeProvider.now();
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    Account account = this.accountRepository.saveAndFlush(new AccountEntity("nickname", this.instant));
    this.entityManager.clear();
    Credential expected = new CredentialEntity(account, "nickname", this.securityEncoder.encode("password"),
        this.instant);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    Credential actual = this.dao.create(new UuidContext(randomUUID()), expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Credential::getAccount, Credential::getPublicKey, Creatable::getCreatedAt)
        .containsSequence(account, "nickname", this.instant);
    assertThat(actual.getId())
        .isPositive();
    Assertions.assertThat(this.securityEncoder.matches("password", actual.getSecretHash()))
        .isTrue();
  }
}
