package kr.lul.kobalttown.account.dao;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.repository.AccountRepository;
import kr.lul.kobalttown.account.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountDaoImpl implements AccountDao {
  private static final Logger log = LoggerFactory.getLogger(AccountDaoImpl.class);

  @Autowired
  private AccountRepository repository;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.repository, "repository is not autowired.");
  }

  @Override
  public Account read(UuidContext context, long id) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, id={}", context, id);

    AccountEntity account = this.repository.findById(id)
        .orElse(null);

    if (log.isTraceEnabled())
      log.trace("#read return : account={}", account);
    return account;
  }
}
