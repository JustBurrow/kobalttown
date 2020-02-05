package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.service.params.*;

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
   * 신청한 신규 계정 정보를 인증한다.
   *
   * @param params 인증 정보.
   *
   * @return 인증된 계정.
   */
  Account enable(EnableAccountParams params);

  /**
   * 계정 조회.
   *
   * @param params 조회할 계정 정보.
   *
   * @return 계정. 없으면 {@code null}.
   */
  Account read(ReadAccountParams params);

  /**
   * 계정 정보 인증 코드를 신규 발급한다.
   *
   * @param params 계정 인증 코드를 신규 발급할 계정의 정보.
   *
   * @return 신규 발급한 계정 인증 코드.
   */
  EnableCode issue(IssueEnableCodeParams params);

  /**
   * 계정의 비밀번호를 변경한다.
   *
   * @param params 비밀번호 변경 정보.
   */
  void update(UpdatePasswordParams params);
}
