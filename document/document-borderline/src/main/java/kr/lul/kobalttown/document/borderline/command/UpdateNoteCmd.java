package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class UpdateNoteCmd extends ContextContainer {
  private long user;
  private long note;

  private String body;

  private Instant timestamp;

  public UpdateNoteCmd(final Context context, final long user, final long note, final String body, final Instant timestamp) {
    super(context);
    this.user = user;
    this.note = note;
    this.body = body;
    this.timestamp = timestamp;
  }

  public UpdateNoteCmd(final ContextContainer container, final long user, final long note, final String body,
      final Instant timestamp) {
    super(container);
    this.user = user;
    this.note = note;
    this.body = body;
    this.timestamp = timestamp;
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
               .append(", body=").append(singleQuote(head(this.body, 100)))
               .append(", timestamp=").append(this.timestamp)
               .append('}')
               .toString();
  }
}
