package kr.lul.kobalttown.account.test;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.data.repository.AccountRepository;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/08
 */
@Service
public class AccountTestToolImpl implements AccountTestTool {
  protected static final Logger log = getLogger(AccountTestToolImpl.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CredentialRepository credentialRepository;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private SecurityEncoder securityEncoder;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.test.AccountTestTool
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account account(String nickname, final boolean enabled, String email, String userKey, String password,
      Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#account args : nickname={}, enabled={}, email={}, userKey={}, password={}, createdAt={}",
          nickname, enabled, email, userKey, password, createdAt);

    if (null == nickname)
      nickname = nickname();
    if (null == email || email.isEmpty())
      email = email();
    if (null == userKey || userKey.isEmpty())
      userKey = userKey();
    if (null == password || password.isEmpty())
      password = DEFAULT_PASSWORD;
    if (null == createdAt)
      createdAt = this.timeProvider.now();

    if (log.isDebugEnabled())
      log.debug("#account create test account with : nickname={}, enabled={}, email={}, userKey={}, password={}, createdAt={}",
          nickname, enabled, email, userKey, password, createdAt);
    Account account = null;
    do {
      try {
        account = this.accountRepository.save(new AccountEntity(nickname, enabled, createdAt));
      } catch (final Exception ignored) {
      }
    } while (null == account);

    this.credentialRepository.save(new CredentialEntity(account, userKey, this.securityEncoder.encode(password), createdAt));
    this.credentialRepository.save(new CredentialEntity(account, email, this.securityEncoder.encode(password), createdAt));

    this.accountRepository.flush();
    if (log.isTraceEnabled())
      log.trace("#account return : {}", account);
    return account;
  }
}
