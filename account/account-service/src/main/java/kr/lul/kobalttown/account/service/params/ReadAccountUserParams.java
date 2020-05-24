package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.util.Objects;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2020/04/26
 */
public class ReadAccountUserParams extends UserParams {
  private long account;

  public ReadAccountUserParams(final TimestampedContext context, final Account user, final long account) {
    super(context, user);
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
    final ReadAccountUserParams that = (ReadAccountUserParams) o;
    return this.account == that.account;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.account);
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%s, account=%d, timestamp=%s}", this.id, this.user.toSimpleString(), this.account, this.timestamp);
  }
}
