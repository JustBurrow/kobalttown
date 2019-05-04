package kr.lul.kobalttown.test.account;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.CreateAccountParams;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * @author justburrow
 * @since 2019-04-20
 */
public class AccountServiceTestUtil extends AccountDaoTestUtil {
  @Autowired
  private AccountService accountService;

  private CreateAccountParams createAccountParams() {
    String nickname;
    do {
      nickname = nickname();
    } while (this.accountRepository.existsByNickname(nickname));

    return new CreateAccountParams(UUID.randomUUID(), this.timeProvider.now(), nickname, DEFAULT_PASSWORD);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.test.account.AccountDaoTestUtil
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Account createdAccount() {
    try {
      return this.accountService.create(createAccountParams());
    } catch (UsedNicknameException e) {
      throw new RuntimeException(e);
    }
  }
}