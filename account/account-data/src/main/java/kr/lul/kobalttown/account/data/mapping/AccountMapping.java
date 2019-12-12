package kr.lul.kobalttown.account.data.mapping;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import java.util.List;

/**
 * @author justburrow
 * @since 2019/11/23
 */
public abstract class AccountMapping {
  public static final String ENTITY = "Account";
  public static final Class ENTITY_TYPE = Account.class;
  public static final String TABLE = "user_account";

  public static final String COL_ID = "id";
  public static final String COL_NICKNAME = "nickname";
  public static final String COL_ENABLED = "enabled";
  public static final String COL_CREATED_AT = SavableEntity.COL_CREATED_AT;
  public static final String COL_UPDATED_AT = SavableEntity.COL_UPDATED_AT;

  public static final String UQ_NICKNAME = "UQ_NICKNAME";
  public static final List<String> UQ_NICKNAME_COLUMNS = List.of(COL_NICKNAME);

  public AccountMapping() {
    throw new UnsupportedOperationException();
  }
}
