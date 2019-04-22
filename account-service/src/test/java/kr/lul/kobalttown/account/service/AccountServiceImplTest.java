package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.dao.AccountDao;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.Creatable;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.support.spring.security.SecretHashEncoder;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.AccountServiceTestConfiguration;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static kr.lul.kobalttown.test.account.AccountDomainTestUtil.nickname;
import static kr.lul.kobalttown.test.account.AccountJpaTestUtil.DEFAULT_PASSWORD;
import static kr.lul.kobalttown.test.account.AccountJpaTestUtil.DEFAULT_PASSWORD_BYTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountServiceTestConfiguration.class)
@Transactional
public class AccountServiceImplTest {
  private static final Logger log = getLogger(AccountServiceImplTest.class);

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private SecretHashEncoder secretHashEncoder;

  @Autowired
  private AccountServiceTestUtil testUtil;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.accountService).isNotNull();
    assertThat(this.accountDao).isNotNull();
    assertThat(this.secretHashEncoder).isNotNull();
    assertThat(this.testUtil).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.accountService.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_create() throws Exception {
    // Given
    UUID trackingId = randomUUID();
    String nickname = nickname();

    CreateAccountParams params = new CreateAccountParams(trackingId, this.before);

    params.setNickname(nickname);
    params.setPassword(DEFAULT_PASSWORD_BYTES);
    log.info("GIVEN - params={}", params);

    // When
    Account account = this.accountService.create(params);
    List<Credential> credentials = this.accountDao.readCredentials(account);
    log.info("WHEN - account={}, credentials={}", account, credentials);

    // Then
    assertThat(account)
        .isNotNull()
        .extracting(Account::getNickname)
        .isEqualToComparingFieldByField(nickname);
    assertThat(account.getId())
        .isGreaterThan(0);
    assertThat(account.getCreatedAt())
        .isEqualTo(this.before)
        .isEqualTo(account.getUpdatedAt());

    for (Credential credential : credentials) {
      assertThat(credential)
          .isNotNull()
          .extracting(Credential::getAccount, Creatable::getCreatedAt)
          .containsSequence(account, account.getCreatedAt());
      assertThat(credential.getId())
          .isGreaterThan(0L);
      assertThat(credential.getPublicKey())
          .isIn(account.getNickname());
      assertThat(this.secretHashEncoder.matches(DEFAULT_PASSWORD, credential.getSecretHash()))
          .isTrue();
    }
  }

  @Test
  public void test_create_with_null_nickname_params() throws Exception {
    // Given
    CreateAccountParams params = new CreateAccountParams(randomUUID(), this.before);
    params.setPassword(DEFAULT_PASSWORD);
    log.info("GIVEN - params={}", params);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(params))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params.nickname is null.");
  }

  @Test
  public void test_create_with_empty_nickname_params() throws Exception {
    // Given
    CreateAccountParams params = new CreateAccountParams(randomUUID(), this.before);
    params.setNickname("");
    params.setPassword(DEFAULT_PASSWORD);
    log.info("GIVEN - params={}", params);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(params))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params.nickname is empty.");
  }

  @Test
  public void test_create_with_null_password() throws Exception {
    // Given
    CreateAccountParams params = new CreateAccountParams(randomUUID(), this.before);
    params.setNickname(nickname());
    log.info("GIVEN - params={}", params);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(params))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params.password is null.");
  }

  @Test
  public void test_create_with_empty_password() throws Exception {
    // Given
    CreateAccountParams params = new CreateAccountParams(randomUUID(), this.before);
    params.setNickname(nickname());
    params.setPassword(new byte[0]);
    log.info("GIVEN - params={}", params);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(params))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params.password is empty.");
  }

  @Test
  public void test_create_with_empty_password_string() throws Exception {
    // Given
    CreateAccountParams params = new CreateAccountParams(randomUUID(), this.before);
    params.setNickname(nickname());
    params.setPassword("");
    log.info("GIVEN - params={}", params);

    // When & Then
    assertThatThrownBy(() -> this.accountService.create(params))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params.password is empty.");
  }
}