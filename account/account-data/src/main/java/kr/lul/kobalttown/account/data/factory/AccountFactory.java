package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public interface AccountFactory {
  Account create(UuidContext context, String nickname, Instant createdAt);
}
