package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountBorderline {
  SimpleAccountDto create(CreateAccountCmd cmd) throws UsedNicknameException;
}