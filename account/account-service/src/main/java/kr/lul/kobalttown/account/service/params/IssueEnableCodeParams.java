package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.AnonymousParams;

import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class IssueEnableCodeParams extends AnonymousParams {
  private String email;

  public IssueEnableCodeParams(final TimestampedContext context, final String email) {
    super(context);
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
    return this.email.equals(((IssueEnableCodeParams) o).email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), this.email, this.timestamp);
  }

  @Override
  public String toString() {
    return format("{id=%s, email='%s', timestamp=%s}", this.id, this.email, this.timestamp);
  }
}
