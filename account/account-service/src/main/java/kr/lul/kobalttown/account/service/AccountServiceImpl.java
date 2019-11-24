package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.dao.AccountDao;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;
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

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountDao, "accountDao is not autowired.");
  }

  @Override
  public Account read(ReadAccountParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);

    return null;
  }
}
