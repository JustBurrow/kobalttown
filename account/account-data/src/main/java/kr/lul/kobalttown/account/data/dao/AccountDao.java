package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountDao {
  Account create(Context context, Account account);

  Account read(Context context, long maxValue);
}
