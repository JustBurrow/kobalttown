package kr.lul.kobalttown.account.jpa.mapping;

import kr.lul.kobalttown.support.spring.jpa.entity.UpdatableMappedSuperclass;

import static kr.lul.kobalttown.account.jpa.mapping.AccountMapping.E.ATTR_NICKNAME;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public final class AccountMapping {
  public static final class E {
    public static final String NAME = "Account";

    public static final String ATTR_ID = "id";
    public static final String ATTR_NICKNAME = "nickname";
    public static final String ATTR_CREATED_AT = UpdatableMappedSuperclass.ATTR_CREATED_AT;
    public static final String ATTR_UPDATED_AT = UpdatableMappedSuperclass.ATTR_UPDATED_AT;
  }

  public static final class T {
    public static final String NAME = "user_account";

    public static final String COL_ID = "id";
    public static final String COL_NICKNAME = ATTR_NICKNAME;
    public static final String COL_CREATED_AT = UpdatableMappedSuperclass.COL_CREATED_AT;
    public static final String COL_UPDATED_AT = UpdatableMappedSuperclass.COL_UPDATED_AT;

    public static final String UQ_ACCOUNT_NICKNAME = "UQ_ACCOUNT_NICKNAME";
    public static final String[] UQ_ACCOUNT_NICKNAME_COLUMNS = new String[]{COL_NICKNAME};
  }
}