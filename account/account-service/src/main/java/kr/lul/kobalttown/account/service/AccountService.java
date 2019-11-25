package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountService {
  Account create(CreateAccountParams params);

  Account read(ReadAccountParams params);
}
