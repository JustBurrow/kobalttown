package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class CreateNoteParams extends ContextContainer {
  private Account user;
  private String body;
  private Instant timestamp;

  public CreateNoteParams(final ContextContainer container, final Account user, final String body, final Instant timestamp) {
    this(container.getContext(), user, body, timestamp);
  }

  public CreateNoteParams(final Context context, final Account user, final String body, final Instant timestamp) {
    super(context);
    notNull(user, "user");
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.body = body;
    this.timestamp = timestamp;
  }

  public Account getUser() {
    return this.user;
  }

  public String getBody() {
    return this.body;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("{context=%s, author=%s, body=%s, timestamp=%s}",
        this.context, this.user.toSimpleString(), singleQuote(head(this.body, 20)), this.timestamp);
  }
}
