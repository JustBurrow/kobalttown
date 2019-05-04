package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.test.account.AccountJpaTestUtil;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import test.configuration.AccountBorderlineTestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-05-04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountBorderlineTestConfiguration.class)
public class AccountBorderlineImplTest {
  private static final Logger log = getLogger(AccountBorderlineImplTest.class);

  @Autowired
  private AccountBorderline accountBorderline;
  @Autowired
  private AccountServiceTestUtil testUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountBorderline).isNotNull();
    assertThat(this.testUtil).isNotNull();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountBorderline.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("cmd is null.");
  }

  @Test
  public void test_create() throws Exception {
    // Given
    CreateAccountCmd cmd = new CreateAccountCmd(this.testUtil.unusedNickname(), AccountJpaTestUtil.DEFAULT_PASSWORD);
    String nickname = cmd.getNickname();
    log.info("GIVEN - cmd={}", cmd);

    // When
    SimpleAccountDto dto = this.accountBorderline.create(cmd);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(SimpleAccountDto::getNickname)
        .isEqualTo(nickname);
    assertThat(dto.getId())
        .isGreaterThan(0);
  }
}