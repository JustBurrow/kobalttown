package kr.lul.kobalttown.page.global;

/**
 * @author justburrow
 * @since 2020/01/18
 */
public abstract class GlobalMvc {
  public static abstract class M {
    protected M() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class V {
    public static final String GROUP = "error";
    public static final String ERROR_404 = GROUP + "/client/404";

    protected V() {
      throw new UnsupportedOperationException();
    }
  }

  public static abstract class C {
    public static final String ERROR = "/error";

    protected C() {
      throw new UnsupportedOperationException();
    }
  }

  protected GlobalMvc() {
    throw new UnsupportedOperationException();
  }
}
