package kr.lul.kobalttown.account.api;

import kr.lul.kobalttown.common.api.Api;
import kr.lul.kobalttown.common.api.ApiImpl;
import kr.lul.kobalttown.common.api.Verbs;

import java.util.Map;

import static kr.lul.kobalttown.account.api.AccountApis.Inputs.CREATE_ATTR;
import static kr.lul.kobalttown.account.api.AccountApis.Inputs.CREATE_TYPE;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public final class AccountApis {
  public static final String NAMESPACE = "/accounts";

  public static final class ApiNames {
    public static final String CREATE_FORM = "/create";
    public static final String CREATE = "";
  }

  public static final class ApiFullNames {
    public static final String CREATE_FORM = NAMESPACE + ApiNames.CREATE_FORM;
    public static final String CREATE = NAMESPACE + ApiNames.CREATE;
  }

  public static final class Inputs {
    public static final String CREATE_ATTR = "account";
    public static final Class CREATE_TYPE = CreateAccountInput.class;
  }

  public static final Api CREATE_FORM = new ApiImpl(Verbs.READ, ApiFullNames.CREATE_FORM);
  public static final Api CREATE = new ApiImpl(Verbs.CREATE, ApiFullNames.CREATE,
      Map.of(CREATE_ATTR, CREATE_TYPE), Map.of());
}