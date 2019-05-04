package kr.lul.kobalttown.account.api;

import kr.lul.kobalttown.common.api.Api;
import kr.lul.kobalttown.common.api.ApiImpl;
import kr.lul.kobalttown.common.api.Verbs;

import java.util.Map;

import static kr.lul.kobalttown.account.api.AccountApis.Attributes.CREATE_ATTR;
import static kr.lul.kobalttown.account.api.AccountApis.Attributes.CREATE_TYPE;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public final class AccountApis {
  public static final String NAMESPACE = "/accounts";

  public static final class ApiNames {
    public static final String CREATE_FORM = "/create";
    public static final String CREATE = "";

    protected ApiNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class ApiFullNames {
    public static final String CREATE_FORM = NAMESPACE + ApiNames.CREATE_FORM;
    public static final String CREATE = NAMESPACE + ApiNames.CREATE;

    protected ApiFullNames() {
      throw new UnsupportedOperationException();
    }
  }

  public static final class Attributes {
    public static final String CREATE_ATTR = "account";
    public static final Class CREATE_TYPE = CreateAccountInput.class;

    protected Attributes() {
      throw new UnsupportedOperationException();
    }
  }

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public static final Api CREATE_FORM = new ApiImpl(Verbs.READ, ApiFullNames.CREATE_FORM);
  public static final Api CREATE = new ApiImpl(Verbs.CREATE, ApiFullNames.CREATE,
      Map.of(CREATE_ATTR, CREATE_TYPE),
      Map.of());

  protected AccountApis() {
    throw new UnsupportedOperationException();
  }
}