package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Creatable;
import kr.lul.common.data.Updatable;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.data.factory.AccountFactory;
import kr.lul.kobalttown.account.data.factory.ValidationCodeFactory;
import kr.lul.kobalttown.account.data.repository.ValidationCodeRepository;
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

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.ValidationCode.Status.ISSUED;
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
  private ValidationCodeFactory factory;
  @Autowired
  private ValidationCodeRepository repository;

  @Autowired
  private AccountDao accountDao;
  @Autowired
  private AccountFactory accountFactory;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    this.before = Instant.now();
  }

  @Test
  public void test_create_with_null_context() throws Exception {
    // GIVEN
    final Account account = this.accountDao.create(new Context(), new AccountEntity(nickname(), false, this.before));
    log.info("GIVEN - account={}", account);
    final ValidationCode validationCode = new ValidationCodeEntity(account, email(), code(), ttl(), this.before);
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
    final String email = email();
    final String code = code();
    final ValidationCode expected = new ValidationCodeEntity(account, email, code, this.before);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final ValidationCode actual = this.dao.create(context, expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail,
            ValidationCode::getCode, ValidationCode::getExpireAt, ValidationCode::getStatus, ValidationCode::getStatusAt,
            ValidationCode::isUsed, ValidationCode::isExpired, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, email,
            code, this.before.plus(TTL_DEFAULT), ISSUED, this.before,
            false, false, this.before, this.before);
    assertThat(actual.getId())
        .isPositive();
  }

  @Test
  public void test_list_with_null_email() throws Exception {
    assertThatThrownBy(() -> this.dao.list(new Context(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("email is null.");
  }

  @Test
  public void test_list_with_empty_email() throws Exception {
    assertThatThrownBy(() -> this.dao.list(new Context(), ""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("email is empty.");
  }

  @Test
  public void test_list() throws Exception {
    // GIVEN
    final Context context = new Context();
    log.info("GIVEN - context={}", context);
    final Account account =
        this.accountDao.create(context, this.accountFactory.create(context, nickname(), false, this.before));
    log.info("GIVEN - account={}", account);
    final String email = email();
    final String code = code();
    final Duration ttl = ttl();
    final ValidationCode validationCode =
        this.dao.create(context, this.factory.create(context, account, email, code, ttl, this.before));
    log.info("GIVEN - validationCode={}", validationCode);

    this.repository.flush();

    // WHEN
    final List<ValidationCode> list = this.dao.list(new Context(), email);
    log.info("GIVEN - list={}", list);

    // THEN
    assertThat(list)
        .isNotNull()
        .hasSize(1)
        .containsOnly(validationCode);
    assertThat(list.get(0))
        .isNotNull()
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail, ValidationCode::getCode, ValidationCode::getStatus,
            ValidationCode::getStatusAt, ValidationCode::isUsed, ValidationCode::isExpired,
            Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, email, code, ISSUED,
            this.before, false, false,
            this.before, this.before);
  }
}
