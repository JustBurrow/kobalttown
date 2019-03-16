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
  @NotEmpty(message = "{err.account.create.nickname-required}")
  private String nickname;
  @NotEmpty(message = "{err.account.create.password-required}")
  private String password;
  @NotEmpty(message = "{err.account.create.confirm-required}")
  private String confirm;

  public CreateAccountInput() {
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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
        .add("nickname=" + singleQuote(this.nickname))
        .add("password=[ PROTECTED ], confirm=[ PROTECTED ]")
        .toString();
  }
}