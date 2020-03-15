package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public class ReadNoteCmd extends ContextContainer {
  /**
   * 노트를 요청한 계정 ID.
   *
   * @see Account#getId()
   */
  private long user;
  /**
   * 노트 ID.
   *
   * @see Note#getId()
   */
  private long note;
  /**
   * 요청 시각.
   */
  private Instant timestamp;

  public ReadNoteCmd(final Context context, final long user, final long note, final Instant timestamp) {
    super(context);
    positive(user, "account");
    positive(note, "note");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.note = note;
    this.timestamp = timestamp;
  }

  public ReadNoteCmd(final ContextContainer container, final long user, final long note, final Instant timestamp) {
    super(container);
    positive(user, "account");
    positive(note, "note");
    notNull(timestamp, "timestamp");

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
    return format("{context=%s, user=%d, note=%d, timestamp=Ts}", this.context, this.user, this.note, this.timestamp);
  }
}
