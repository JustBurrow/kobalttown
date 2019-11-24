package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.service.AccountService;
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
class AccountBorderlineImpl implements AccountBorderline {
  private static final Logger log = getLogger(AccountBorderlineImpl.class);

  @Autowired
  private AccountService accountService;
  @Autowired
  private AccountConverter accountConverter;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountService, "accountService is not autowired.");
    requireNonNull(this.accountConverter, "accountConverter is not autowired.");
  }
}
