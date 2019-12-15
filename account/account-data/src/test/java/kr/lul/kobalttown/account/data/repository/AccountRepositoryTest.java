package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDataRepositoryTestConfiguration.class)
@Transactional
public class AccountRepositoryTest {
  private static final Logger log = getLogger(AccountRepositoryTest.class);

  @Autowired
  private AccountRepository repository;

  @Before
  public void setUp() throws Exception {
    assertThat(this.repository).isNotNull();
  }

  @Test
  public void test_findAll() throws Exception {
    final List<AccountEntity> list = this.repository.findAll();

    assertThat(list)
        .isNotNull();
  }

  @Test
  public void test_save() throws Exception {
    // GIVEN
    final String nickname = "test";
    final Instant createdAt = Instant.now();
    log.info("GIVEN - nickname={}, createdAt={}", nickname, createdAt);
    final AccountEntity expected = new AccountEntity(nickname, createdAt);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final AccountEntity actual = this.repository.save(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(AccountEntity::getNickname, AccountEntity::isEnabled,
            SavableEntity::getCreatedAt, SavableEntity::getUpdatedAt)
        .containsSequence(nickname, false, createdAt, createdAt);
    assertThat(actual.getId())
        .isPositive();
  }
}
