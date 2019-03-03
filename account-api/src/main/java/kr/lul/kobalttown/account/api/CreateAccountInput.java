package kr.lul.kobalttown.account.api;

import kr.lul.kobalttown.common.validation.Confirm;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Confirm(target = "password")
public class CreateAccountInput {
  @NotEmpty
  private String id;
  @NotEmpty
  private String password;
  @NotEmpty
  private String confirm;

  public CreateAccountInput() {
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return this.confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", CreateAccountInput.class.getSimpleName() + "[", "]")
        .add("id=" + singleQuote(this.id))
        .add("password=[ PROTECTED ], confirm=[ PROTECTED ]")
        .toString();
  }
}