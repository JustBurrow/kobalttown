package kr.lul.kobalttown.account.dao;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.domain.Account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountDao {
  Account read(UuidContext context, long maxValue);
}
