package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.AnonymousCmd;

import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class IssueEnableCodeCmd extends AnonymousCmd {
  private String email;

  public IssueEnableCodeCmd(final TimestampedContext context, final String email) {
    this(context, email, context.getTimestamp());
  }

  public IssueEnableCodeCmd(final Context context, final String email, final Instant timestamp) {
    super(context, timestamp);
    notEmpty(email, "email");

    this.email = email;
  }

  public String getEmail() {
    return this.email;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    return this.email.equals(((IssueEnableCodeCmd) o).email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.email);
  }

  @Override
  public String toString() {
    return format("{id=%s, email='%s', timestamp=%s}", this.id, this.email, this.timestamp);
  }
}
