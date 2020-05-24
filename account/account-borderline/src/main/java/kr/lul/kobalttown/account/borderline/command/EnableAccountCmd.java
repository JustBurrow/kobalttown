package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.AnonymousCmd;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/01/05
 */
public class EnableAccountCmd extends AnonymousCmd {
  private String token;
  private Instant timestamp;

  public EnableAccountCmd(final TimestampedContext context, final String token) {
    this(context, token, context.getTimestamp());
  }

  public EnableAccountCmd(final Context context, final String token, final Instant timestamp) {
    super(context, timestamp);
    notEmpty(token);

    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  @Override
  public String toString() {
    return format("{id=%s, token='%s', timestamp=%s}", this.id, this.token, this.timestamp);
  }
}
