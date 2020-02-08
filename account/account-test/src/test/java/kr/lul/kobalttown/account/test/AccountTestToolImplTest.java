package kr.lul.kobalttown.account.test;

import kr.lul.kobalttown.account.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountTestToolTestConfiguration.class)
public class AccountTestToolImplTest {
  protected static final Logger log = getLogger(AccountTestToolImplTest.class);

  @Autowired
  private AccountTestTool tool;

  @Before
  public void setUp() throws Exception {
    assertThat(this.tool).isNotNull();
  }

  @Test
  public void test_account() throws Exception {
    // WHEN
    final Account account = this.tool.account();
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNotNull();
    assertThat(account.getId())
        .isPositive();
  }
}
