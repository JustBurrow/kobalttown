package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

import java.util.List;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountDao {
  Account create(Account account);

  Credential create(Credential credential);

  boolean isUsedNickname(String nickname);

  List<Credential> readCredentials(Account account);
}