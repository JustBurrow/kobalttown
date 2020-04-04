package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/04/04
 */
public class DeleteNoteCommentParams extends ContextContainer {
  private Account user;
  private Note note;
  private long comment;
  private Instant timestamp;

  public DeleteNoteCommentParams(final Context context, final Account user, final Note note, final long comment, final Instant timestamp) {
    super(context);
    this.user = user;
    this.note = note;
    this.comment = comment;
    this.timestamp = timestamp;
  }

  public DeleteNoteCommentParams(
      final ContextContainer container, final Account user, final Note note, final long comment, final Instant timestamp) {
    this(container.getContext(), user, note, comment, timestamp);
  }

  public Account getUser() {
    return this.user;
  }

  public Note getNote() {
    return this.note;
  }

  public long getComment() {
    return this.comment;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{context=").append(this.context)
               .append(", user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", comment=").append(this.comment)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
