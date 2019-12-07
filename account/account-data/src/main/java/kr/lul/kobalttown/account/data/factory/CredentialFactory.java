package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.UuidContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public interface CredentialFactory {
  Credential create(UuidContext context, Account account, String publicKey, String secretHash, Instant createdAt);
}
