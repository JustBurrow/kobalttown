package kr.lul.kobalttown.article.jpa.mapping;

import kr.lul.kobalttown.support.spring.jpa.entity.CreatableMappedSuperclass;

/**
 * @author justburrow
 * @since 2019-03-31
 */
public final class ArticleMapping {
  public static final class E {
    public static final String NAME = "Article";

    public static final String ATTR_ID = "id";
    public static final String ATTR_TITLE = "title";
    public static final String ATTR_SUMMARY = "summary";
    public static final String ATTR_BODY = "body";
    public static final String ATTR_CREATOR = "creator";
    public static final String ATTR_CREATED_AT = CreatableMappedSuperclass.ATTR_CREATED_AT;

    public E() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class T {
    public static final String NAME = "user_article";

    public static final String COL_ID = E.ATTR_ID;
    public static final String COL_TITLE = E.ATTR_TITLE;
    public static final String COL_SUMMARY = E.ATTR_SUMMARY;
    public static final String COL_BODY = E.ATTR_BODY;
    public static final String COL_CREATOR = E.ATTR_CREATOR;
    public static final String COL_CREATED_AT = CreatableMappedSuperclass.COL_CREATED_AT;

    public static final String FK_ARTICLE_PK_ACCOUNT = "FK_ARTICLE_PK_ACCOUNT";
    public static final String FK_ARTICLE_PK_ACCOUNT_COLUMNS = COL_CREATOR + " ASC";

    public T() {
      throw new UnsupportedOperationException();
    }
  }

  public ArticleMapping() {
    throw new UnsupportedOperationException();
  }
}