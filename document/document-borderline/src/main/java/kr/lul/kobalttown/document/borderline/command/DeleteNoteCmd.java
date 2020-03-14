package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class DeleteNoteCmd extends ContextContainer {
  private long user;
  private long note;
  private Instant timestamp;

  public DeleteNoteCmd(final Context context, final long user, final long note, final Instant timestamp) {
    super(context);
    this.user = user;
    this.note = note;
    this.timestamp = timestamp;
  }

  public DeleteNoteCmd(final ContextContainer container, final long user, final long note, final Instant timestamp) {
    super(container);
    this.user = user;
    this.note = note;
    this.timestamp = timestamp;
  }

  public long getUser() {
    return this.user;
  }

  public long getNote() {
    return this.note;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", timestamp=").append(this.timestamp)
               .append(", context=").append(this.context)
               .append('}').toString();
  }
}
