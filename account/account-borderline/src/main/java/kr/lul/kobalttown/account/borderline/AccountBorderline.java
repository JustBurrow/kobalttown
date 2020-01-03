package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;

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
   * 등록된 계정 정보 조회.
   *
   * @param cmd 조회할 계정 정보.
   *
   * @return 계정 정보. 없으면 {@code null}.
   */
  AccountDetailDto read(ReadAccountCmd cmd);
}
