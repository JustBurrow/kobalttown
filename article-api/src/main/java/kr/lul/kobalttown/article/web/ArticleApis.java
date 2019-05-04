package kr.lul.kobalttown.article.web;

import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.common.api.Api;
import kr.lul.kobalttown.common.api.ApiImpl;
import kr.lul.kobalttown.common.api.Verbs;

import java.util.Map;

/**
 * @author justburrow
 * @since 2019-05-03
 */
public final class ArticleApis {
  public static final String NAMESPACE = "/articles";

  public static final class ApiNames {
    public static final String CREATE_FORM = "/create";
    public static final String CREATE = "";
    public static final String READ = "/{id}";

    private ApiNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class ApiFullNames {
    public static final String CREATE_FORM = NAMESPACE + ApiNames.CREATE_FORM;
    public static final String CREATE = NAMESPACE + ApiNames.CREATE;
    public static final String READ = NAMESPACE + ApiNames.READ;

    private ApiFullNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class Attributes {
    public static final String CREATE_ATTR = "article";
    public static final Class CREATE_TYPE = CreateArticleInput.class;

    public static final String ID_ATTR = "id";
    public static final Class ID_TYPE = Long.TYPE;

    public static final String ARTICLE_ATTR = "article";
    public static final Class ARTICLE_TYPE = DetailArticleDto.class;

    private Attributes() {
      throw new UnsupportedOperationException();
    }
  }

  public static final Api CREATE_FORM = new ApiImpl(Verbs.READ, ApiFullNames.CREATE_FORM);
  public static final Api CREATE = new ApiImpl(Verbs.CREATE, ApiFullNames.CREATE,
      Map.of(Attributes.CREATE_ATTR, Attributes.CREATE_TYPE),
      Map.of());
  public static final Api READ = new ApiImpl(Verbs.READ, ApiFullNames.READ,
      Map.of(Attributes.ID_ATTR, Attributes.ID_TYPE),
      Map.of(Attributes.ARTICLE_ATTR, Attributes.ARTICLE_TYPE));

  protected ArticleApis() {
    throw new UnsupportedOperationException();
  }
}