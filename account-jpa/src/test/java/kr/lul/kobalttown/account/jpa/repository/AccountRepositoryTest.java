package kr.lul.kobalttown.account.jpa.repository;

import kr.lul.kobalttown.account.jpa.AccountJpaTestConfiguration;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountRepository).isNotNull();
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
}