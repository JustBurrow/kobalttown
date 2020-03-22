package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public class CreateNoteCommentParams extends ContextContainer {
  private Account user;
  private Note note;
  private String body;
  private Instant timestamp;

  public CreateNoteCommentParams(final Context context, final Account user, final Note note, final String body,
      final Instant timestamp) {
    super(context);
    notNull(user, "user");
    notNull(note, "note");
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.note = note;
    this.body = body;
    this.timestamp = timestamp;
  }

  public CreateNoteCommentParams(
      final ContextContainer container, final Account user, final Note note, final String body, final Instant timestamp) {
    this(container.getContext(), user, note, body, timestamp);
  }

  public Account getUser() {
    return this.user;
  }

  public Note getNote() {
    return this.note;
  }

  public String getBody() {
    return this.body;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{context=").append(this.context)
               .append(", user=").append(this.user.toSimpleString())
               .append(", note=").append(this.note.toSimpleString())
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
