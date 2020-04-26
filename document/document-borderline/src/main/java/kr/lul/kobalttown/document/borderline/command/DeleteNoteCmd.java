package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class DeleteNoteCmd extends UserCmd {
  private long note;

  public DeleteNoteCmd(final Context context, final long user, final long note, final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");

    this.note = note;
  }

  public long getNote() {
    return this.note;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
