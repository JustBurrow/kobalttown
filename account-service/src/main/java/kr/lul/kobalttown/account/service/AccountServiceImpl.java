package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.dao.AccountDao;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.support.spring.security.SecretHashEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.kobalttown.common.util.Arguments.notEmpty;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static kr.lul.kobalttown.common.util.Texts.singleQuote;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Service
class AccountServiceImpl implements AccountService {
  private static final Logger log = getLogger(AccountServiceImpl.class);

  @Autowired
  private SecretHashEncoder secretHashEncoder;
  @Autowired
  private AccountDao accountDao;

  @PostConstruct
  private void postConstruct() {
    if (log.isInfoEnabled()) {
      log.info("postConstruct : secretHashEncoder={}, accountDao={}",
          this.secretHashEncoder, this.accountDao);
    }
  }

  private Credential initCredential(Account account, CreateAccountParams params) {
    return new CredentialEntity(account,
        params.getNickname(),
        this.secretHashEncoder.encode(new String(params.getPassword(), UTF_8)),
        params.getTimestampMillis());
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.service.AccountService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account create(CreateAccountParams params) throws UsedNicknameException {
    if (log.isTraceEnabled()) {
      log.trace("args : params={}", params);
    }
    notNull(params, "params");
    notEmpty(params.getNickname(), "params.nickname");
    notEmpty(params.getPassword(), "params.password");

    if (this.accountDao.isUsedNickname(params.getNickname())) {
      throw new UsedNicknameException(format("nickname=%s", singleQuote(params.getNickname())));
    }

    Account account = new AccountEntity(params.getNickname(), params.getTimestampMillis());
    account = this.accountDao.create(account);

    Credential credential = initCredential(account, params);
    credential = this.accountDao.create(credential);

    if (log.isTraceEnabled()) {
      log.trace("result : account={}, credential={}", account, credential);
    }
    return account;
  }
}