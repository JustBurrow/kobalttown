package kr.lul.kobalttown.article.web.view;

/**
 * @author justburrow
 * @since 2019-05-03
 */
public final class ArticleView {
  public static final String NAME_PREFIX = "page/articles/";

  public static final String CREATE_FORM = NAME_PREFIX + "create";
  public static final String READ = NAME_PREFIX + "detail";

  protected ArticleView() {
    throw new UnsupportedOperationException();
  }
}