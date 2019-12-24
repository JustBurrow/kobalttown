package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.domain.Account;
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
import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
@Transactional
public class AccountDaoImplTest {
  private static final Logger log = getLogger(AccountDaoImplTest.class);

  @Autowired
  private AccountDao dao;
  @Autowired
  private TimeProvider timeProvider;

  private Instant instant;

  @Before
  public void setUp() throws Exception {
    assertThat(this.dao).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.instant = this.timeProvider.now();
    log.info("SETUP - instant={}", this.instant);
  }

  @Test
  public void test_read_with_context_and_not_exist_id() throws Exception {
    // GIVEN
    final Context context = new Context();
    log.info("GIVEN - context={}", context);

    // WHEN
    final Account account = this.dao.read(context, Long.MAX_VALUE);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .isNull();
  }

  @Test
  public void test_create_with_null_context_and_null_account() throws Exception {
    assertThatThrownBy(() -> this.dao.create(null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.");
  }

  @Test
  public void test_create_with_context_and_null_account() throws Exception {
    assertThatThrownBy(() -> this.dao.create(new Context(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("account is null.");
  }

  @Test
  public void test_create_with_context_and_account() throws Exception {
    // GIVEN
    final String nickname = "nickname #" + current().nextInt(MAX_VALUE);
    log.info("GIVEN - nickname={}", nickname);
    final AccountEntity expected = new AccountEntity(nickname, false, this.instant);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final Account actual = this.dao.create(new Context(), expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getNickname, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(nickname, false, this.instant, this.instant);
    assertThat(actual.getId())
        .isPositive();
  }

  @Test
  public void test_read_with_context_and_exist_id() throws Exception {
    // GIVEN
    final String nickname = "nickname #" + random(current().nextInt(1, NICKNAME_MAX_LENGTH - 9));
    final Account expected = this.dao.create(new Context(), new AccountEntity(nickname, false, this.instant));
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final Account actual = this.dao.read(new Context(), expected.getId());
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(Account::getId, Account::isEnabled, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(expected.getId(), false, this.instant, this.instant);
  }
}
