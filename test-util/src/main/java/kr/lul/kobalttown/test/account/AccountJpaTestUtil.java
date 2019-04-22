package kr.lul.kobalttown.test.account;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.Instant;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.kobalttown.common.util.Arguments.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class AccountJpaTestUtil extends AccountDomainTestUtil {
  public static final String DEFAULT_PASSWORD = "TZ90iMwwKsbi5aacSn18DKWrsdOFNW4XHv7gSMbl9NAuX4Y1cp";
  public static final byte[] DEFAULT_PASSWORD_BYTES = DEFAULT_PASSWORD.getBytes(UTF_8);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  protected TimeProvider timeProvider;
  @Autowired
  private PasswordEncoder passwordEncoder;


  @PostConstruct
  private void postConstruct() {
    assertThat(this.accountRepository).isNotNull();
    assertThat(this.credentialRepository).isNotNull();
    assertThat(this.timeProvider).isNotNull();
    assertThat(this.passwordEncoder).isNotNull();
  }

  /**
   * 제약조건을 위반하지 않는 새 계정을 반환한다.
   *
   * @return 새로 저장할 수 있는 계정.
   */
  public AccountEntity prePersistAccount() {
    String nickname;
    do {
      nickname = nickname();
    } while (this.accountRepository.existsByNickname(nickname));

    return new AccountEntity(nickname, this.timeProvider.now());
  }

  /**
   * 새로 생성, DB 저장한 계정을 반환한다.
   *
   * @return 새로 저장한 계정.
   */
  public AccountEntity persistedAccount() {
    return persistedAccount(this.timeProvider.now());
  }

  /**
   * 새로 생성, DB 저장한 계정을 반환한다.
   *
   * @param createdAt 생성 시각.
   *
   * @return 새로 저장한 계정.
   */
  public AccountEntity persistedAccount(Instant createdAt) {
    notNull(createdAt, "createdAt");

    return this.accountRepository.save(new AccountEntity(nickname(), createdAt));
  }

  /**
   * 제약조건을 위반하지 않는 새 인증정보를 만든다.
   * 공개키로 계정의 별명을 사용하고, {@link #DEFAULT_PASSWORD 기본 비밀번호}를 사용한다.
   *
   * @param account 인증할 계정.
   *
   * @return 새 인증 정보.
   */
  public CredentialEntity prePersistCredential(AccountEntity account) {
    return prePersistCredential(account, DEFAULT_PASSWORD);
  }

  /**
   * 제약조건을 위반하지 않는 새 인증정보를 만든다.
   * 공개키로 계정의 별명을 사용한다.
   *
   * @param account  인증할 계정.
   * @param password 비밀번호(평문).
   *
   * @return 새 인증 정보.
   */
  public CredentialEntity prePersistCredential(AccountEntity account, String password) {
    notNull(account, "account");
    notEmpty(password, "password");

    if (this.credentialRepository.existsByPublicKey(account.getNickname())) {
      throw new IllegalArgumentException(format("used publicKey(nickname) : '%s'", account.getNickname()));
    }

    return new CredentialEntity(account, account.getNickname(), this.passwordEncoder.encode(password),
        this.timeProvider.now());
  }

  /**
   * 기본 비밀번호를 사용하는 새 계정과 인증정보를 저장하고 반환한다.
   *
   * @return 새로 저장된 인증정보.
   */
  public CredentialEntity persistedCredential() {
    AccountEntity account = persistedAccount();

    return this.credentialRepository.save(new CredentialEntity(account,
        account.getNickname(),
        this.passwordEncoder.encode(DEFAULT_PASSWORD),
        this.timeProvider.now()));
  }

  /**
   * 기본 비밀번호를 사용하는 새 인증정보를 저장하고 반환한다.
   *
   * @param account 인증할 계정.
   *
   * @return 새로 저장된 인증정보.
   */
  public CredentialEntity persistedCredential(AccountEntity account) {
    notNull(account, "account");
    positive(account.getId(), "account.id");

    return this.credentialRepository.save(new CredentialEntity(account,
        account.getNickname(),
        this.passwordEncoder.encode(DEFAULT_PASSWORD),
        this.timeProvider.now()));
  }
}