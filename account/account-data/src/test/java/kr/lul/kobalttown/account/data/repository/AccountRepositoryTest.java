package kr.lul.kobalttown.account.data.repository;

import kr.lul.kobalttown.account.data.AccountDataModuleTestConfiguration;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@DataJdbcTest
@ContextConfiguration(classes = AccountDataModuleTestConfiguration.class)
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
    List<AccountEntity> list = this.repository.findAll();

    assertThat(list)
        .isEmpty();
  }
}
