package kr.lul.kobalttown.account.service;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountServiceTestConfiguration.class)
@Transactional
public class AccountServiceImplTest {
  private static final Logger log = getLogger(AccountServiceImplTest.class);

  @Autowired
  private AccountService service;

  @Before
  public void setUp() throws Exception {
    assertThat(this.service).isNotNull();
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    ReadAccountParams params = new ReadAccountParams(new UuidContext(randomUUID()), Long.MAX_VALUE);
    log.info("GIVEN - params={}", params);

    // WHEN
    Account account = this.service.read(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
  }
}
