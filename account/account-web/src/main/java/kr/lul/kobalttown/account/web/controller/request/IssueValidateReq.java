package kr.lul.kobalttown.account.web.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class IssueValidateReq {
  @NotEmpty
  @Email
  private String email;

  public IssueValidateReq() {
  }

  public IssueValidateReq(final String email) {
    setEmail(email);
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final IssueValidateReq that = (IssueValidateReq) o;
    return Objects.equals(this.email, that.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.email);
  }

  @Override
  public String toString() {
    return format("%s{email=%s}", IssueValidateReq.class.getSimpleName(), singleQuote(this.email));
  }
}
