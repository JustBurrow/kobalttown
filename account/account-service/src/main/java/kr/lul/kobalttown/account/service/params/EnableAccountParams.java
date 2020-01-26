package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/01/05
 */
public class EnableAccountParams extends ContextContainer {
  private String token;
  private Instant timestamp;

  public EnableAccountParams(final Context context, final String token, final Instant timestamp) {
    super(context);
    notEmpty(token, "validationCode");
    notNull(timestamp, "timestamp");

    this.token = token;
    this.timestamp = timestamp;
  }

  public String getToken() {
    return this.token;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder(EnableAccountParams.class.getSimpleName())
               .append("{context=").append(this.context)
               .append(", token=").append(singleQuote(this.token))
               .append(", timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
