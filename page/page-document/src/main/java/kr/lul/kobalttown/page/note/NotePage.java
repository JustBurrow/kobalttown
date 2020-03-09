package kr.lul.kobalttown.page.note;

import kr.lul.common.web.api.Action;
import kr.lul.common.web.api.Api;
import kr.lul.kobalttown.page.note.NoteMvc.C;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public enum NotePage {
  CREATE_FORM(new Api(Action.READ, C.GROUP, C.CREATE_FORM), "신규 노트 작성 폼."),
  CREATE(new Api(Action.CREATE, C.GROUP, C.CREATE), "신규 노트 작성.");

  public final Api api;
  public final String description;

  NotePage(final Api api, final String description) {
    this.api = api;
    this.description = description;
  }
}
