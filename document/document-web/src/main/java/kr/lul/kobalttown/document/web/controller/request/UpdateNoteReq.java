package kr.lul.kobalttown.document.web.controller.request;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class UpdateNoteReq {
  private String body;

  public UpdateNoteReq() {
  }

  public UpdateNoteReq(final String body) {
    this.body = body;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(final String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return format("{body=%s}", singleQuote(this.body));
  }
}
