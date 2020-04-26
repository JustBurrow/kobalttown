package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class UpdateNoteCmd extends UserCmd {
  private long note;
  private String body;

  public UpdateNoteCmd(final Context context, final long user, final long note, final String body, final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");
    notNull(body, "body");

    this.note = note;
    this.body = body;
  }

  public long getNote() {
    return this.note;
  }

  public String getBody() {
    return this.body;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", body=").append(singleQuote(head(this.body, 100)))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
