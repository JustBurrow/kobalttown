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
    public static final String ENABLED_AT = "enabledAt";
    public static final String ISSUE_ENABLE_CODE = "issueEnableCode";
    public static final String ENABLE_CODE = "enableCode";

    public M() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class V {
    public static final String GROUP = "layout/account";

    public static final String CREATE_FORM = GROUP + "/create";
    public static final String LIST = GROUP + "/list";
    public static final String DETAIL = GROUP + "/detail";
    public static final String ENABLE_SUCCESS = GROUP + "/enable_success";
    public static final String ENABLE_FAIL = GROUP + "/enable_fail";
    public static final String ISSUE_ENABLE_CODE = GROUP + "/issue_enable_code";
    public static final String ENABLE_CODE_ISSUED = GROUP + "/enable_code_issued";

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

    public static final String ENABLE = GROUP + "/enable/{" + M.TOKEN + "}";
    public static final String ISSUE_ENABLE_CODE_FORM = GROUP + "/enable/issue";
    public static final String ISSUE_ENABLE_CODE = GROUP + "/enable";

    public C() {
      throw new UnsupportedOperationException();
    }
  }

  public AccountMvc() {
    throw new UnsupportedOperationException();
  }
}
