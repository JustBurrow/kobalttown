package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Credential;

import java.util.UUID;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface CredentialDao {
  Credential create(Context<UUID> uuidContext, Credential credential);
}
