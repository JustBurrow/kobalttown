package kr.lul.kobalttown.account.borderline;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountBorderlineTestConfiguration.class)
public class AccountBorderlineImplTest {
  private static final Logger log = getLogger(AccountBorderlineImplTest.class);

  @Autowired
  private AccountBorderline borderline;

  @Before
  public void setUp() throws Exception {
    assertThat(this.borderline).isNotNull();
  }

  @Test
  public void test_read_with_not_exist_id() throws Exception {
    // GIVEN
    long id = Long.MAX_VALUE;
    log.info("GIVEN - id={}", id);
    ReadAccountCmd cmd = new ReadAccountCmd(new UuidContext(UUID.randomUUID()), id);
    log.info("GIVEN - cmd={}", cmd);

    // WHEN
    AccountDetailDto account = this.borderline.read(cmd);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
  }
}
