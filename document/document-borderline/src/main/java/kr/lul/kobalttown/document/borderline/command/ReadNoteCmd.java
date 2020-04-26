package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public class ReadNoteCmd extends UserCmd {
  /**
   * 노트 ID.
   *
   * @see Note#getId()
   */
  private long note;

  public ReadNoteCmd(final Context context, final long user, final long note, final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");

    this.note = note;
  }

  public long getNote() {
    return this.note;
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%d, note=%d, timestamp=Ts}", this.id, this.user, this.note, this.timestamp);
  }
}
