package kr.lul.kobalttown.article.web;

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
    public static final String READ = "{id}";

    protected ApiNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class ApiFullNames {
    public static final String CREATE_FORM = NAMESPACE + ApiNames.CREATE_FORM;
    public static final String CREATE = NAMESPACE + ApiNames.CREATE;
    public static final String READ = NAMESPACE + ApiNames.READ;

    protected ApiFullNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class Inputs {
    public static final String CREATE_ATTR = "article";
    public static final Class CREATE_TYPE = CreateArticleInput.class;

    protected Inputs() {
      throw new UnsupportedOperationException();
    }
  }

  public static final Api CREATE_FORM = new ApiImpl(Verbs.READ, ApiFullNames.CREATE_FORM);
  public static final Api CREATE = new ApiImpl(Verbs.CREATE, ApiFullNames.CREATE,
      Map.of(Inputs.CREATE_ATTR, Inputs.CREATE_TYPE),
      Map.of());

  protected ArticleApis() {
    throw new UnsupportedOperationException();
  }
}