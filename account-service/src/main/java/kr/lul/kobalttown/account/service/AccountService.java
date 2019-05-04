package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountService {
  /**
   * 신규 계정 등록.
   *
   * @param params 계정 생성에 필요한 정보.
   *
   * @return 신규 생성, 저장된 계정.
   *
   * @throws UsedNicknameException 이미 사용중인 닉네임으로 계정 생성을 시도한 경우.
   */
  Account create(CreateAccountParams params) throws UsedNicknameException;

  /**
   * 계정 조회
   *
   * @param id 계정 ID.
   *
   * @return 계정. 없으면 {@code null}.
   */
  Account read(int id);
}