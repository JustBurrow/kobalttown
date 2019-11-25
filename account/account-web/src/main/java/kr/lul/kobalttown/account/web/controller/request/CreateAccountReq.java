package kr.lul.kobalttown.account.web.controller.request;

import javax.validation.constraints.*;

import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_REGEX;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MIN_LENGTH;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class CreateAccountReq {
  @NotEmpty
  @Size(max = NICKNAME_MAX_LENGTH)
  @Pattern(regexp = NICKNAME_REGEX)
  private String nickname;

  @NotNull
  @Email
  private String email;

  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String password;

  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String confirm;

  public CreateAccountReq() {
  }

  public CreateAccountReq(String nickname, String email, String password, String confirm) {
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.confirm = confirm;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountReq.class.getSimpleName())
        .append("{nickname=").append(singleQuote(this.nickname))
        .append(", email=").append(singleQuote(this.email))
        .append(", password='[ PROTECTED ]', confirm=' PROTECTED '}")
        .toString();
  }
}
