package kr.lul.kobalttown.account.data.mapping;

import kr.lul.support.spring.data.jpa.entiy.CreatableEntity;

import java.util.List;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class CredentialMapping {
  public static final String ENTITY = "Credential";
  public static final String TABLE = "user_credential";

  public static final String COL_ID = "id";
  public static final String COL_ACCOUNT = "account";
  public static final String COL_PUBLIC_KEY = "public_key";
  public static final String COL_SECRET_HASH = "secret_hash";
  public static final String COL_CREATED_AT = CreatableEntity.COL_CREATED_AT;

  public static final String FK_CREDENTIAL_PK_ACCOUNT = "FK_CREDENTIAL_PK_ACCOUNT";
  public static final String FK_CREDENTIAL_PK_ACCOUNT_COLUMNS = COL_ACCOUNT + " ASC";

  public static final String UQ_CREDENTIAL_PUBLIC_KEY = "UQ_CREDENTIAL_PUBLIC_KEY";
  public static final List<String> UQ_CREDENTIAL_PUBLIC_KEY_COLUMNS = List.of(COL_PUBLIC_KEY);

  public CredentialMapping() {
    throw new UnsupportedOperationException();
  }
}