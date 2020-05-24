package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;
import java.util.UUID;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class CreateNoteParams extends UserParams {
  private String body;

  public CreateNoteParams(final TimestampedContext context, final Account user, final String body) {
    this(context.getId(), user, body, context.getTimestamp());
  }

//  public CreateNoteParams(final Context context, final Account user, final String body, final Instant timestamp) {
//    this(context.getId(), user, body, timestamp);
//  }

  public CreateNoteParams(final UUID context, final Account user, final String body, final Instant timestamp) {
    super(context, user, timestamp);
    notNull(body, "body");
    notNull(timestamp, "timestamp");

    this.body = body;
  }

  public String getBody() {
    return this.body;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("{id=%s, user=%s, body=%s, timestamp=%s}",
        this.id, this.user.toSimpleString(), singleQuote(head(this.body, 20)), this.timestamp);
  }
}
