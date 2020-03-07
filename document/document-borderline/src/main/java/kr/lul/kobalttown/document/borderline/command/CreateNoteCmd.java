package kr.lul.kobalttown.document.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * 신규 노트 작성 커맨드.
 *
 * @author justburrow
 * @since 2020/03/07
 */
public class CreateNoteCmd extends ContextContainer {
  /**
   * 작성자 ID.
   */
  private long author;
  /**
   * 내용.
   */
  private String body;
  private Instant timestamp;

  public CreateNoteCmd(final Context context, final long author, final String body, final Instant timestamp) {
    super(context);
    positive(author, "author");
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.author = author;
    this.body = body;
    this.timestamp = timestamp;
  }

  public CreateNoteCmd(final ContextContainer container, final long author, final String body, final Instant timestamp) {
    super(container);
    positive(author, "author");
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.author = author;
    this.body = body;
    this.timestamp = timestamp;
  }

  public long getAuthor() {
    return this.author;
  }

  public String getBody() {
    return this.body;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return format("{context=%s, author=%d, body='%s', timestamp=%s}",
        this.context, this.author, this.body, this.timestamp);
  }
}
