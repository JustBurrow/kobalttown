package kr.lul.kobalttown.account.web.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

import static kr.lul.kobalttown.account.domain.Credential.SECRET_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MIN_LENGTH;

/**
 * @author justburrow
 * @since 2020/01/30
 */
public class UpdatePasswordReq {
  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String password;

  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String confirm;

  public UpdatePasswordReq() {
  }

  public UpdatePasswordReq(final String password, final String confirm) {
    setPassword(password);
    setConfirm(confirm);
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public String getConfirm() {
    return this.confirm;
  }

  public void setConfirm(final String confirm) {
    this.confirm = confirm;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final UpdatePasswordReq that = (UpdatePasswordReq) o;
    return Objects.equals(this.password, that.password) &&
               Objects.equals(this.confirm, that.confirm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.password, this.confirm);
  }

  @Override
  public String toString() {
    return "{password=[ PROTECTED ], confirm=[ PROTECTED ]}";
  }
}
