package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.EnableAccountCmd;
import kr.lul.kobalttown.account.borderline.command.IssueEnableCodeCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountBorderline {
  /**
   * 신규 계정 등록.
   *
   * @param cmd 신규 계정 정보.
   *
   * @return 등록된 신규 계정 정보.
   */
  AccountDetailDto create(CreateAccountCmd cmd);

  /**
   * 신규 계정을 인증한다.
   *
   * @param cmd 인증 요청 정보.
   *
   * @return 인증된 계정 정보. 없으면 {@code null}.
   */
  AccountDetailDto enable(EnableAccountCmd cmd);

  /**
   * 등록된 계정 정보 조회.
   *
   * @param cmd 조회할 계정 정보.
   *
   * @return 계정 정보. 없으면 {@code null}.
   */
  AccountDetailDto read(ReadAccountCmd cmd);

  /**
   * 신규 계정의 인증 코드를 재발급한다.
   *
   * @param cmd 인증 코드를 재발급할 계정 정보.
   *
   * @return 재발급한 코드.
   */
  EnableCodeSummaryDto issue(IssueEnableCodeCmd cmd);
}
