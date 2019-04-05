package kr.lul.kobalttown.account.jpa.repository;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.jpa.AccountJpaTestConfiguration;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
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
public class AccountRepositoryTest {
  private static final Logger log = getLogger(AccountRepositoryTest.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.accountJpaTestUtil).isNotNull();
  }

  @Test
  public void test_findAll() throws Exception {
    // When
    List<AccountEntity> list = this.accountRepository.findAll();
    log.info("WHEN - list={}", list);

    // Then
    assertThat(list)
        .isNotNull()
        .isEmpty();
  }

  @Test
  public void test_save_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountRepository.save(null))
        .isNotNull();
  }

  @Test
  public void test_save() throws Exception {
    // Given
    AccountEntity account = this.accountJpaTestUtil.prePersistAccount();
    String nickname = account.getNickname();
    Instant createdAt = account.getCreatedAt();
    log.info("GIVEN - account={}", account);

    // When
    AccountEntity actual = this.accountRepository.save(account);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getNickname, Account::getCreatedAt, Account::getUpdatedAt)
        .containsSequence(nickname, createdAt, createdAt);
    assertThat(actual.getId())
        .isGreaterThan(0);
  }
}