package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.domain.Account;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/07
 */
@Service
class AccountFactoryImpl implements AccountFactory {
  private static final Logger log = getLogger(AccountFactoryImpl.class);

  @Override
  public Account create(Context context, String nickname, Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, nickname={}, createdAt={}", context, nickname, createdAt);
    notNull(context, "context");

    AccountEntity account = new AccountEntity(nickname, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, account);
    return account;
  }
}
