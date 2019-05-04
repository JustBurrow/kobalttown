package kr.lul.kobalttown.root.web.view;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public final class RootView {
  public static final String NAME_PREFIX = "page/_/";

  public static final String VIEW_INDEX_ROLE_ANONYMOUS = NAME_PREFIX + "index-anonymous";
  public static final String VIEW_INDEX_ROLE_USER = NAME_PREFIX + "index-user";

  public RootView() {
    throw new UnsupportedOperationException();
  }
}