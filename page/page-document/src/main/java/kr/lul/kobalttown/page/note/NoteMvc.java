package kr.lul.kobalttown.page.note;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public abstract class NoteMvc {
  public static class M {
    public static final String NOTE = "note";
    public static final String ID = "id";
    public static final String NOTES = "notes";
    public static final String CREATE_REQ = "createReq";
    public static final String UPDATE_REQ = "updateReq";
    public static final String CREATE_COMMENT_REQ = "commentReq";
    public static final String COMMENT = "comment";

    protected M() {
      throw new UnsupportedOperationException();
    }
  }

  public static class V {
    public static final String GROUP = "layout/note";

    public static final String INDEX = GROUP + "/index";
    public static final String CREATE = GROUP + "/create";
    public static final String DETAIL = GROUP + "/detail";
    public static final String UPDATE = GROUP + "/update";

    protected V() {
      throw new UnsupportedOperationException();
    }
  }

  public static class C {
    public static final String GROUP = "/notes";

    public static final String CREATE_FORM = GROUP + "/create";
    public static final String CREATE = GROUP;

    public static final String INDEX = GROUP;
    public static final String DETAIL = GROUP + "/{id:[1-9]\\d*}";

    public static final String UPDATE_FORM = GROUP + "/{id:[1-9]\\d*}/update";
    public static final String UPDATE = GROUP + "/{id:[1-9]\\d*}";

    public static final String DELETE = GROUP + "/{id:[1-9]\\d*}";

    public static final String CREATE_COMMENT = GROUP + "/{id:[1-9]\\d*}/comments";
    public static final String DELETE_COMMENT = GROUP + "/{note:[1-9]\\d*}/comments/{comment:[1-9]\\d*}";

    protected C() {
      throw new UnsupportedOperationException();
    }
  }

  protected NoteMvc() {
    throw new UnsupportedOperationException();
  }
}
