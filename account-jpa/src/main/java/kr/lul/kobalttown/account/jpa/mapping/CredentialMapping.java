package kr.lul.kobalttown.account.jpa.mapping;

import kr.lul.kobalttown.support.spring.jpa.entity.CreatableMappedSuperclass;

import static kr.lul.kobalttown.account.jpa.mapping.CredentialMapping.E.ATTR_ACCOUNT;
import static kr.lul.kobalttown.account.jpa.mapping.CredentialMapping.E.ATTR_ID;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public final class CredentialMapping {
  public static final class E {
    public static final String NAME = "Credential";

    public static final String ATTR_ID = "id";
    public static final String ATTR_ACCOUNT = "account";
    public static final String ATTR_PUBLIC_KEY = "publicKey";
    public static final String ATTR_SECRET_HASH = "secretHash";
    public static final String ATTR_CREATED_AT = CreatableMappedSuperclass.ATTR_CREATED_AT;
  }

  public static final class T {
    public static final String NAME = "user_credential";

    public static final String COL_ID = ATTR_ID;
    public static final String COL_ACCOUNT = ATTR_ACCOUNT;
    public static final String COL_PUBLIC_KEY = "public_key";
    public static final String COL_SECRET_HASH = "secret_hash";
    public static final String COL_CREATED_AT = CreatableMappedSuperclass.COL_CREATED_AT;

    public static final String FK_CREDENTIAL_PK_ACCOUNT = "FK_CREDENTIAL_PK_ACCOUNT";
    public static final String FK_CREDENTIAL_PK_ACCOUNT_COLUMNS = COL_ACCOUNT + " ASC";

    public static final String UQ_CREDENTIAL_PUBLIC_KEY = "UQ_CREDENTIAL_PUBLIC_KEY";
    public static final String[] UQ_CREDENTIAL_PUBLIC_KEY_COLUMNS = new String[]{COL_PUBLIC_KEY};
  }
}