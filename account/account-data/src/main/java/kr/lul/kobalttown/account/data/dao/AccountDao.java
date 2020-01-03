package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountDao {
  /**
   * 계정 정보 신규 저장.
   *
   * @param context 컨텍스트.
   * @param account 신규 계정.
   *
   * @return 신규 저장된 계정.
   */
  Account create(Context context, Account account);

  /**
   * 계정 정보 조회.
   *
   * @param context 컨텍스트.
   * @param id      계정 ID.
   *
   * @return 계정. 없으면 {@code null}.
   */
  Account read(Context context, long id);
}
