package kr.lul.kobalttown.page.root;

/**
 * @author justburrow
 * @since 2019/11/25
 */
public abstract class RootMvc {
  public abstract static class M {
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public M() {
      throw new UnsupportedOperationException();
    }
  }

  public abstract static class V {
    public static final String GROUP = "layout/root";

    public static final String INDEX = GROUP + "/index";
    public static final String LOG_IN = GROUP + "/login";

    public V() {
      throw new UnsupportedOperationException();
    }
  }

  public abstract static class C {
    public static final String INDEX = "/";
    public static final String LOG_IN = "/login";
    public static final String LOG_OUT = "/logout";
    public static final String ASSETS = "/assets";

    public static final String ERROR = "/error";

    public C() {
      throw new UnsupportedOperationException();
    }
  }

  public RootMvc() {
    throw new UnsupportedOperationException();
  }
}
