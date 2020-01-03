package kr.lul.kobalttown.account.data.mapping;

import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public abstract class ValidationCodeMapping {
  public static final String ENTITY = "ValidationCode";
  public static final Class ENTITY_TYPE = ValidationCode.class;
  public static final String TABLE = "user_validation_code";

  public static final String COL_ID = "id";
  public static final String COL_ACCOUNT = "account";
  public static final String COL_CODE = "code";
  public static final String COL_EXPIRE_AT = "expire_at";
  public static final String COL_USED_AT = "used_at";
  public static final String COL_EXPIRED_AT = "expired_at";
  public static final String COL_CREATED_AT = SavableEntity.COL_CREATED_AT;
  public static final String COL_UPDATED_AT = SavableEntity.COL_UPDATED_AT;

  public static final String FK_VALIDATION_CODE_PK_ACCOUNT = "FK_VALIDATION_CODE_PK_ACCOUNT";
  public static final String FK_VALIDATION_CODE_PK_ACCOUNT_COLUMNS = COL_ACCOUNT + " ASC";

  public static final String UQ_VALIDATION_CODE = "UQ_VALIDATION_CODE";
  public static final List<String> UQ_VALIDATION_CODE_COLUMNS = List.of(COL_CODE);

  public static final String IDX_VALIDATION_CODE_VALID = "IDX_VALIDATION_CODE_VALID";
  public static final String IDX_VALIDATION_CODE_VALID_COLUMNS = COL_EXPIRE_AT + " DESC, " + COL_USED_AT + " ASC, " + COL_EXPIRED_AT + " ASC";

  protected ValidationCodeMapping() {
    throw new UnsupportedOperationException();
  }
}
