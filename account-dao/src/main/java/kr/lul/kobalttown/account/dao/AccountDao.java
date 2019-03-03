package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountDao {
  Account create(Account account);

  Credential create(Credential credential);
}