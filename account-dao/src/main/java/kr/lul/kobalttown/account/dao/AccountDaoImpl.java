package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.AccountRepository;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.lul.kobalttown.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Service
class AccountDaoImpl implements AccountDao {
  private static final Logger log = getLogger(AccountDaoImpl.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private CredentialRepository credentialRepository;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.dao.AccountDao
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account create(Account account) {
    if (log.isTraceEnabled()) {
      log.trace("args : account={}", account);
    }
    notNull(account, "account");
    notPositive(account.getId(), "account.id");
    typeOf(account, AccountEntity.class, "account");

    account = this.accountRepository.saveAndFlush((AccountEntity) account);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", account);
    }
    return account;
  }

  @Override
  public Credential create(Credential credential) {
    if (log.isTraceEnabled()) {
      log.trace("args : credential={}", credential);
    }
    notNull(credential, "credential");
    typeOf(credential, CredentialEntity.class, "credential");

    credential = this.credentialRepository.saveAndFlush((CredentialEntity) credential);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", credential);
    }
    return credential;
  }

  @Override
  public Account read(int id) {
    if (log.isTraceEnabled()) {
      log.trace("args : id={}", id);
    }

    final Account account = 0 < id
        ? this.accountRepository.findOneById(id)
        : null;

    if (log.isTraceEnabled()) {
      log.trace("return : {}", account);
    }
    return account;
  }

  @Override
  public boolean isUsedNickname(String nickname) {
    if (log.isTraceEnabled()) {
      log.trace("args : nickname={}", nickname);
    }
    notEmpty(nickname, "nickname");

    boolean exists = this.accountRepository.existsByNickname(nickname);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", exists);
    }
    return exists;
  }

  @Override
  public List<Credential> readCredentials(Account account) {
    if (log.isTraceEnabled()) {
      log.trace("args : account={}", account);
    }
    notNull(account, "account");
    positive(account.getId(), "account.id");
    typeOf(account, AccountEntity.class, "account");

    List<Credential> credentials = new ArrayList<>(this.credentialRepository.findAllByAccount((AccountEntity) account));

    if (log.isTraceEnabled()) {
      log.trace("return : {}", credentials);
    }
    return credentials;
  }
}