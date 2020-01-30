package kr.lul.kobalttown.page.account;

import kr.lul.common.web.api.Action;
import kr.lul.common.web.api.Api;

import static kr.lul.kobalttown.page.account.AccountMvc.C;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public enum AccountPage {
  CREATE_FORM(new Api(Action.READ, C.GROUP, C.CREATE_FORM), "신규 계정 등록 폼."),
  CREATE(new Api(Action.CREATE, C.GROUP, C.CREATE), "계정 등록 처리."),

  LIST(new Api(Action.READ, C.GROUP, C.LIST), "신규 계정 목록."),
  DETAIL(new Api(Action.READ, C.GROUP, C.PROFILE), "계정 프로필."),

  ENABLE(new Api(Action.READ, C.GROUP, C.ENABLE), "새로 등록한 계정을 활성화한다."),
  ISSUE_ENABLE_CODE_FORM(new Api(Action.READ, C.GROUP, C.ISSUE_ENABLE_CODE_FORM), "새 계정 활성화 코드 발급 폼."),
  ISSUE_ENABLE_CODE(new Api(Action.CREATE, C.GROUP, C.ISSUE_ENABLE_CODE), "새 계정 활성화 코드 발급.");

  public final Api api;
  public final String description;

  AccountPage(final Api api, final String description) {
    this.api = api;
    this.description = description;
  }
}
