package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNegative;
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
  private int commentsPage;
  private int commentsSize;

  public ReadNoteCmd(final Context context, final long user, final long note, int commentsPage, int commentsSize,
      final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");
    notNegative(commentsPage, "commentsPage");
    positive(commentsSize, "commentsSize");

    this.note = note;
    this.commentsPage = commentsPage;
    this.commentsSize = commentsSize;
  }

  public long getNote() {
    return this.note;
  }

  public int getCommentsPage() {
    return this.commentsPage;
  }

  public int getCommentsSize() {
    return this.commentsSize;
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%d, note=%d, commentsPage=%d, commentsSize=%d, timestamp=%s}",
        this.id, this.user, this.note, this.commentsPage, this.commentsSize, this.timestamp);
  }
}
