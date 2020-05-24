package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.AnonymousParams;

import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/01/05
 */
public class EnableAccountParams extends AnonymousParams {
  private String token;

  public EnableAccountParams(final TimestampedContext context, final String token) {
    super(context);
    notEmpty(token, "validationCode");

    this.token = token;
  }

  public String getToken() {
    return this.token;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    return this.token.equals(((EnableAccountParams) o).token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.token);
  }

  @Override
  public String toString() {
    return format("{id=%s, token='%s', timestamp=%s}", this.id, this.token, this.timestamp);
  }
}
