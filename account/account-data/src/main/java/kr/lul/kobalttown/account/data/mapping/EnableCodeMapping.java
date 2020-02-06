package kr.lul.kobalttown.account.data.mapping;

import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public abstract class EnableCodeMapping {
  public static final String ENTITY = "EnableCode";
  public static final Class<?> ENTITY_TYPE = EnableCode.class;
  public static final String TABLE = "user_enable_code";

  public static final String COL_ID = "id";
  public static final String COL_ACCOUNT = "account";
  public static final String COL_EMAIL = "email";
  public static final String COL_TOKEN = "token";
  public static final String COL_EXPIRE_AT = "expire_at";
  public static final String COL_STATUS = "status";
  public static final String COL_STATUS_AT = "status_at";
  public static final String COL_CREATED_AT = SavableEntity.COL_CREATED_AT;
  public static final String COL_UPDATED_AT = SavableEntity.COL_UPDATED_AT;

  public static final String FK_ENABLE_CODE_PK_ACCOUNT = "fk_enable_code_pk_account";
  public static final String FK_ENABLE_CODE_PK_ACCOUNT_COLUMNS = COL_ACCOUNT + " ASC";

  public static final String FK_ENABLE_CODE_PK_ENABLE_CODE_STATUS = "fk_enable_code_pk_enable_code_status";
  public static final String FK_ENABLE_CODE_PK_ENABLE_CODE_STATUS_COLUMNS = COL_STATUS + " ASC";

  public static final String IDX_ENABLE_CODE_EMAIL = "idx_enable_code_email";
  public static final String IDX_ENABLE_CODE_EMAIL_COLUMNS = COL_EMAIL + " ASC";

  public static final String UQ_ENABLE_CODE_TOKEN = "uq_enable_code_token";
  public static final List<String> UQ_ENABLE_CODE_TOKEN_COLUMNS = List.of(COL_TOKEN);

  public static final String IDX_ENABLE_CODE_STATUS = "idx_enable_code_status";
  public static final String IDX_ENABLE_CODE_STATUS_COLUMNS = COL_STATUS + " ASC, " + COL_STATUS_AT + " DESC";

  protected EnableCodeMapping() {
    throw new UnsupportedOperationException();
  }
}
