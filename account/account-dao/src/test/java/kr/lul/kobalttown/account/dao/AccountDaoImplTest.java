package kr.lul.kobalttown.account.dao;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
public class AccountDaoImplTest {
  private static final Logger log = getLogger(AccountDaoImplTest.class);

  @Autowired
  private AccountDao dao;

  @Before
  public void setUp() throws Exception {
    assertThat(this.dao).isNotNull();
  }

  @Test
  public void test_read_with_context_and_not_exist_id() throws Exception {
    // GIVEN
    UuidContext context = new UuidContext(randomUUID());
    log.info("GIVEN - context={}", context);

    // WHEN
    Account account = this.dao.read(context, Long.MAX_VALUE);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
  }
}
