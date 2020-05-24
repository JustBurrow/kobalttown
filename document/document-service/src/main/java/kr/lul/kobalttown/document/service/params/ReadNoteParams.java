package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;
import java.util.UUID;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class ReadNoteParams extends UserParams {
  /**
   * 요청한 노트 ID.
   */
  private long note;

  public ReadNoteParams(final Context context, final Account user, final long note, final Instant timestamp) {
    this(context.getId(), user, note, timestamp);
  }

  public ReadNoteParams(final UUID context, final Account user, final long note, final Instant timestamp) {
    super(context, user, timestamp);

    this.note = note;
  }

  public long getNote() {
    return this.note;
  }

  @Override
  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("{id=%s, user=%s, note=%d, timestamp=%s}}",
        this.id, this.user.toSimpleString(), this.note, this.timestamp);
  }
}
