package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class IssueValidateCmd extends ContextContainer {
  private String email;
  private Instant timestamp;

  public IssueValidateCmd(final Context context, final String email, final Instant timestamp) {
    super(context);
    notNull(email, "email");
    notNull(timestamp, "timestamp");

    this.email = email;
    this.timestamp = timestamp;
  }

  public String getEmail() {
    return this.email;
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    final IssueValidateCmd that = (IssueValidateCmd) o;
    return this.email.equals(that.email) &&
               this.timestamp.equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.context, this.email, this.timestamp);
  }

  @Override
  public String toString() {
    return format("%s{context=%s, email=%s, timestamp=%s}", IssueValidateCmd.class.getSimpleName(), this.context,
        singleQuote(this.email), this.timestamp);
  }
}
