package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountBorderline {
  AccountDetailDto create(CreateAccountCmd cmd);

  AccountDetailDto read(ReadAccountCmd cmd);
}
