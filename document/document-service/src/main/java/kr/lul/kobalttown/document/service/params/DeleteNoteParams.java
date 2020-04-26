package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;
import java.util.UUID;

import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class DeleteNoteParams extends UserParams {
  private long note;

  public DeleteNoteParams(final Context context, final Account user, final long note, final Instant timestamp) {
    this(context.getId(), user, note, timestamp);
  }

  public DeleteNoteParams(final UUID context, final Account user, final long note, final Instant timestamp) {
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
               .append(", user=").append(this.user.toSimpleString())
               .append(", note=").append(this.note)
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
