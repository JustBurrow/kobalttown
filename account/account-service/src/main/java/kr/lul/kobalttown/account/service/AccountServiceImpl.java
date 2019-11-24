package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.dao.AccountDao;
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
}
