package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class ReadAccountUserCmd extends UserCmd {
  private long account;

  public ReadAccountUserCmd(final TimestampedContext context, final long user, final long account) {
    this(context, user, account, context.getTimestamp());
  }

  public ReadAccountUserCmd(final Context context, final long user, final long account, final Instant timestamp) {
    super(context.getId(), user, timestamp);
    positive(account, "account");

    this.account = account;
  }

  public long getAccount() {
    return this.account;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    return this.account == ((ReadAccountUserCmd) o).account;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.account);
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%d, account=%d, timestamp=%s}", this.id, this.user, this.account, this.timestamp);
  }
}
