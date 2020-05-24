package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.common.SystemParams;

import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.positive;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class ReadAccountSystemParams extends SystemParams {
  private long account;

  public ReadAccountSystemParams(final TimestampedContext context, final long account) {
    this(context, account, context.getTimestamp());
  }

  public ReadAccountSystemParams(final Context context, final long account, final Instant timestamp) {
    super(context.getId(), timestamp);
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
    return this.account == ((ReadAccountSystemParams) o).account;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.account);
  }

  @Override
  public String toString() {
    return format("{id=%s, account=%d, timestamp=%s}", this.id, this.account, this.timestamp);
  }
}
