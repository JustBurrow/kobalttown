package kr.lul.kobalttown.page.account;

import kr.lul.common.web.Action;
import kr.lul.common.web.Api;

import static kr.lul.kobalttown.page.account.AccountMvc.C;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public enum AccountPage {
  CREATE_FORM(new Api(Action.READ, C.GROUP, C.CREATE_FORM)),
  CREATE(new Api(Action.CREATE, C.GROUP, C.CREATE)),

  LIST(new Api(Action.READ, C.GROUP, C.LIST)),
  DETAIL(new Api(Action.READ, C.GROUP, C.DETAIL));

  public final Api api;

  AccountPage(Api api) {
    this.api = api;
  }
}
