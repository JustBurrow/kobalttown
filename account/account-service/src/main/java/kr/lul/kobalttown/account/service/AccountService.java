package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountService {
  /**
   * 신규 계정 등록.
   *
   * @param params 신규 계정 정보.
   *
   * @return 등록된 신규 계정.
   */
  Account create(CreateAccountParams params);

  /**
   * 계정 조회.
   *
   * @param params 조회할 계정 정보.
   *
   * @return 계정. 없으면 {@code null}.
   */
  Account read(ReadAccountParams params);
}
