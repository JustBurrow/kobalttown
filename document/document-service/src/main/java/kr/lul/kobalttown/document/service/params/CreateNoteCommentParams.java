package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public class CreateNoteCommentParams extends UserParams {
  private Note note;
  private String body;

  public CreateNoteCommentParams(final TimestampedContext context, final Account user, final Note note, final String body) {
    this(context, user, note, body, context.getTimestamp());
  }

  public CreateNoteCommentParams(final Context context, final Account user, final Note note, final String body,
      final Instant timestamp) {
    super(context.getId(), user, timestamp);
    notNull(note, "note");
    notNull(body, "body");

    this.note = note;
    this.body = body;
  }

  public Note getNote() {
    return this.note;
  }

  public String getBody() {
    return this.body;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", user=").append(this.user.toSimpleString())
               .append(", note=").append(this.note.toSimpleString())
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
