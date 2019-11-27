package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.data.dao.AccountDao;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = getLogger(AccountServiceImpl.class);

  @Autowired
  private AccountDao accountDao;
  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private SecurityEncoder securityEncoder;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountDao, "accountDao is not autowired.");
  }

  @Override
  public Account create(CreateAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    Account account = new AccountEntity(params.getNickname(), params.getInstant());
    account = this.accountDao.create(params.getContext(), account);

    Credential credential = new CredentialEntity(account, params.getNickname(),
        this.securityEncoder.encode(params.getPassword()), params.getInstant());
    credential = this.credentialDao.create(params.getContext(), credential);

    credential = new CredentialEntity(account, params.getEmail(),
        this.securityEncoder.encode(params.getPassword()), params.getInstant());
    credential = this.credentialDao.create(params.getContext(), credential);

    if (log.isTraceEnabled())
      log.trace("#create return : {}", account);
    return account;
  }

  @Override
  public Account read(ReadAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);
    notNull(params, "params");

    return null;
  }
}
