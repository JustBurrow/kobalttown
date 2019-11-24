package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountMvc {
  public static abstract class M {
    public static final String ID = "id";

    public M() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class V {
    public static final String GROUP = "account";

    public static final String CREATE_FORM = GROUP + "/create";
    public static final String LIST = GROUP + "/list";
    public static final String DETAIL = GROUP + "/detail";

    public V() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class C {
    public static final String GROUP = "/accounts";

    public static final String CREATE_FORM = GROUP + "/create";
    public static final String CREATE = GROUP;

    public static final String LIST = GROUP;
    public static final String DETAIL = GROUP + "/{" + M.ID + ":[1-9]\\d*}";

    public C() {
      throw new UnsupportedOperationException();
    }
  }

  public AccountMvc() {
    throw new UnsupportedOperationException();
  }
}
