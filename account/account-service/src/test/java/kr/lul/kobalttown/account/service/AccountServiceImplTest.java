package kr.lul.kobalttown.account.service;

import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.data.UuidContext;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static java.lang.Integer.MAX_VALUE;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

  @Autowired
  private TimeProvider timeProvider;

  private Instant instant;

  @Before
  public void setUp() throws Exception {
    assertThat(this.service).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.instant = this.timeProvider.now();
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

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.service.create(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    String nickname = "nickname #" + current().nextInt(MAX_VALUE);
    String email = "just.burrow." + current().nextInt(MAX_VALUE) + "@lul.kr";
    CreateAccountParams params = new CreateAccountParams(new UuidContext(), nickname, email, "password", this.instant);
    log.info("GIVEN - params={}", params);

    // WHEN
    Account account = this.service.create(params);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNotNull()
        .extracting(Account::getNickname, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(nickname, false, this.instant, this.instant);
    assertThat(account.getId())
        .isPositive();
  }
}
