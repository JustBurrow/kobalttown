package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountService {
  Account create(CreateAccountParams params) throws UsedNicknameException;
}