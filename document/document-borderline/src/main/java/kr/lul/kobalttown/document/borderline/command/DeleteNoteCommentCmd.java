package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/04/03
 */
public class DeleteNoteCommentCmd extends UserCmd {
  private long note;
  private long comment;

  public DeleteNoteCommentCmd(final Context context, final long user, final long note, final long comment,
      final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");
    positive(comment, "comment");

    this.note = note;
    this.comment = comment;
  }

  public long getNote() {
    return this.note;
  }

  public long getComment() {
    return this.comment;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", comment=").append(this.comment)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
