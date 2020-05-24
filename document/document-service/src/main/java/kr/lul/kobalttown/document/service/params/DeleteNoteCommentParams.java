package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/04/04
 */
public class DeleteNoteCommentParams extends UserParams {
  private long note;
  private long comment;

  public DeleteNoteCommentParams(final Context context, final Account user, final long note, final long comment,
      final Instant timestamp) {
    super(context, user, timestamp);
    notNull(note, "note");
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
               .append(", user=").append(this.user.toSimpleString())
               .append(", note=").append(this.note)
               .append(", comment=").append(this.comment)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
