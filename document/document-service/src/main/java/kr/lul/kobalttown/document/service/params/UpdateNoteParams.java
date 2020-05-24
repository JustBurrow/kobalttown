package kr.lul.kobalttown.document.service.params;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/14
 */
public class UpdateNoteParams extends UserParams {
  private Account user;
  private long note;

  private String body;

  private Instant timestamp;

  public UpdateNoteParams(final Context context, final Account user, final long note, final String body,
      final Instant timestamp) {
    super(context, user, timestamp);
    positive(note, "note");
    notEmpty(body, "body");

    this.note = note;
    this.body = body;
  }

  public long getNote() {
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
               .append(", note=").append(this.note)
               .append(", body=").append(singleQuote(head(this.body, 100)))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
