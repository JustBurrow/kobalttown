package kr.lul.kobalttown.account.data.repository;

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

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.code;
import static kr.lul.kobalttown.account.domain.ValidationCodeUtil.ttl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountDataRepositoryTestConfiguration.class)
@Transactional
public class ValidationCodeRepositoryTest {
  private static final Logger log = getLogger(ValidationCodeRepositoryTest.class);

  @Autowired
  private ValidationCodeRepository repository;
  @Autowired
  private AccountRepository accountRepository;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.repository).isNotNull();
    assertThat(this.accountRepository).isNotNull();

    this.before = Instant.now();
  }

  @Test
  public void test_findAll() throws Exception {
    // WHEN
    final List<ValidationCodeEntity> list = this.repository.findAll();
    log.info("WHEN - list={}", list);

    // THEN
    assertThat(list)
        .isNotNull();
  }

  @Test
  public void test_save() throws Exception {
    // GIVEN
    final Account account = this.accountRepository.save(new AccountEntity(nickname(), false, this.before));
    log.info("GIVEN - account={}", account);
    final String email = email();
    String code;
    do {
      code = code();
    } while (this.repository.existsByCode(code));
    final Duration ttl = ttl();
    final ValidationCodeEntity expected = new ValidationCodeEntity(account, email, code, ttl, this.before);
    log.info("GIVEN - expected={}", expected);

    // WHEN
    final ValidationCode actual = this.repository.saveAndFlush(expected);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNotNull()
        .extracting(ValidationCode::getAccount, ValidationCode::getEmail, ValidationCode::getCode,
            ValidationCode::getExpireAt, ValidationCode::isUsed, ValidationCode::getUsedAt,
            ValidationCode::isExpired, ValidationCode::getExpiredAt, Creatable::getCreatedAt, Updatable::getUpdatedAt)
        .containsSequence(account, email, code,
            this.before.plus(ttl), false, null,
            false, null, this.before, this.before);
    assertThat(actual.getId())
        .isPositive();
  }
}
