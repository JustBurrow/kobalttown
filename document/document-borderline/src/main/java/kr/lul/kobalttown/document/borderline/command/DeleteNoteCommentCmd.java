package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/04/03
 */
public class DeleteNoteCommentCmd extends ContextContainer {
  private long user;
  private long note;
  private long comment;
  private Instant timestamp;

  public DeleteNoteCommentCmd(final Context context, final long user, final long note, final long comment,
      final Instant timestamp) {
    super(context);
    this.user = user;
    this.note = note;
    this.comment = comment;
    this.timestamp = timestamp;
  }

  public DeleteNoteCommentCmd(final ContextContainer container,
      final long user, final long note, final long comment, final Instant timestamp) {
    this(container.getContext(), user, note, comment, timestamp);
  }

  public long getUser() {
    return this.user;
  }

  public long getNote() {
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
