package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class DeleteNoteParams extends ContextContainer {
  private Account user;
  private long note;
  private Instant timestamp;

  public DeleteNoteParams(final Context context, final Account user, final long note, final Instant timestamp) {
    super(context);

    this.user = user;
    this.note = note;
    this.timestamp = timestamp;
  }

  public DeleteNoteParams(final ContextContainer container, final Account user, final long note, final Instant timestamp) {
    this(container.getContext(), user, note, timestamp);
  }

  public Account getUser() {
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
