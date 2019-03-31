package kr.lul.kobalttown.account.domain;

import kr.lul.kobalttown.common.util.Creatable;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public interface Credential extends Creatable {
  String SECRET_HASH_REGEX = "\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}";

  long getId();

  Account getAccount();

  String getPublicKey();

  String getSecretHash();
}