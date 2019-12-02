package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountMvc {
  public static abstract class M {
    public static final String ID = "id";
    public static final String CREATE_REQ = "createReq";
    public static final String ACCOUNT = "account";
    public static final String TOKEN = "token";

    public M() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class V {
    public static final String GROUP = "layout/account";

    public static final String CREATE_FORM = GROUP + "/create";
    public static final String LIST = GROUP + "/list";
    public static final String DETAIL = GROUP + "/detail";
    public static final String ACTIVATE = GROUP + "/activate";

    public V() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class C {
    public static final String GROUP = "/accounts";

    public static final String CREATE_FORM = "/create";
    public static final String CREATE = "";
    public static final String LIST = "";
    public static final String DETAIL = "/{" + M.ID + ":[1-9]\\d*}";
    public static final String ACTIVATE = "/activate/{" + M.TOKEN + "}";

    public static final String FULL_CREATE_FORM = GROUP + "/create";
    public static final String FULL_CREATE = GROUP;
    public static final String FULL_LIST = GROUP;
    public static final String FULL_DETAIL = GROUP + "/{" + M.ID + ":[1-9]\\d*}";
    public static final String FULL_ACTIVATE = GROUP + "/activate/{" + M.TOKEN + "}";

    public C() {
      throw new UnsupportedOperationException();
    }
  }

  public AccountMvc() {
    throw new UnsupportedOperationException();
  }
}
