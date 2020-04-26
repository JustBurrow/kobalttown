package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * 신규 노트 작성 커맨드.
 *
 * @author justburrow
 * @since 2020/03/07
 */
public class CreateNoteCmd extends UserCmd {
  /**
   * 내용.
   */
  private String body;

  public CreateNoteCmd(final Context context, final long user, final String body, final Instant timestamp) {
    super(context, user, timestamp);
    notNull(body, "body");

    this.body = body;
  }

  public String getBody() {
    return this.body;
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%d, body='%s', timestamp=%s}",
        this.id, this.user, this.body, this.timestamp);
  }
}
