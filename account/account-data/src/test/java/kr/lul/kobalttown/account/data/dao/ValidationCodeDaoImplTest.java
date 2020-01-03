package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.ValidationCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDaoTestConfiguration.class)
@Transactional
public class ValidationCodeDaoImplTest {
  private static final Logger log = getLogger(ValidationCodeDaoImplTest.class);

  @Autowired
  private ValidationCodeDao dao;

  @Autowired
  private AccountDao accountDao;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.dao).isNotNull();
    assertThat(this.accountDao).isNotNull();

    this.before = Instant.now();
  }

  @Test
  public void test_create_with_null_context() throws Exception {
    // GIVEN
    final Account account = this.accountDao.create(new Context(), new AccountEntity(nickname(), false, this.before));
    log.info("GIVEN - account={}", account);
    final ValidationCode validationCode = new ValidationCodeEntity(account, code(), ttl(), this.before);
    log.info("GIVEN - validationCode={}", validationCode);

    // WHEN & THEN
    assertThatThrownBy(() -> this.dao.create(null, validationCode))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("context is null.")
        .hasNoCause();
  }

  @Test
  public void test_create_with_null_validationCode() throws Exception {
    assertThatThrownBy(() -> this.dao.create(new Context(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("validationCode is null.")
        .hasNoCause();
  }

  @Test
  public void test_create() throws Exception {
    // GIVEN
    final Context context = new Context();
    log.info("GIVEN - context={}", context);
    final Account account = this.accountDao.create(context, new AccountEntity(nickname(), false, this.before));
    log.info("GIVEN - account={}", account);
    final String code = code();
    final ValidationCode expected = new ValidationCodeEntity(account, code, this.before);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final ValidationCode actual = this.dao.create(context, expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(ValidationCode::getAccount, ValidationCode::getCode, ValidationCode::getExpireAt,
            ValidationCode::isUsed, ValidationCode::getUsedAt, ValidationCode::isExpired, ValidationCode::getExpiredAt,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, code, this.before.plus(TTL_DEFAULT),
            false, null, false, null,
            this.before, this.before);
    assertThat(actual.getId())
        .isPositive();
  }
}
