package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public class CreateNoteCommentCmd extends ContextContainer {
  private long user;
  private long note;
  private String body;
  private Instant timestamp;

  public CreateNoteCommentCmd(final Context context, final long user, final long note, final String body,
      final Instant timestamp) {
    super(context);
    positive(user, "user");
    positive(note, "note");
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.note = note;
    this.body = body;
    this.timestamp = timestamp;
  }

  public CreateNoteCommentCmd(final ContextContainer container, final long user, final long note, final String body,
      final Instant timestamp) {
    this(container.getContext(), user, note, body, timestamp);
  }

  public long getUser() {
    return this.user;
  }

  public long getNote() {
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
               .append(", user=").append(this.user)
               .append(", note=").append(this.note)
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
