package kr.lul.kobalttown.test.account.jpa;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.account.domain.CredentialUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * {@link CredentialEntity} 관련 테스트 유틸리티.
 *
 * @author justburrow
 * @since 2019-04-03
 */
public class CredentialEntityUtil {
  private static final Logger log = getLogger(CredentialEntityUtil.class);

  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private TimeProvider timeProvider;

  /**
   * 제약조건을 위반하지 않는 새 인증정보를 만든다.
   * 공개키로 계정의 별명을 사용한다.
   *
   * @param account 인증할 계정.
   *
   * @return 새 인증 정보.
   */
  public CredentialEntity freshCredential(AccountEntity account) {
    log.trace("args : account={}", account);
    return freshCredential(account, CredentialUtils.password());
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
  public CredentialEntity freshCredential(AccountEntity account, String password) {
    log.trace("args : account={}, password={}", account, password);
    notNull(account, "account");
    positive(account.getId(), "account.id");
    notEmpty(password, "password");

    if (this.credentialRepository.existsByPublicKey(account.getNickname())) {
      throw new IllegalArgumentException(format("used publicKey(nickname) : '%s'", account.getNickname()));
    }

    CredentialEntity credential = new CredentialEntity(account, account.getNickname(),
        this.passwordEncoder.encode(password), this.timeProvider.now());

    log.debug("return : {}", credential);
    return credential;
  }
}