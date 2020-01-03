package kr.lul.kobalttown.account.web.controller.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_REGEX;
import static kr.lul.kobalttown.account.domain.Credential.*;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class CreateAccountReq {
  @NotEmpty
  @Size(max = NICKNAME_MAX_LENGTH)
  @Pattern(regexp = NICKNAME_REGEX)
  private String nickname;

  @NotEmpty
  @Length(min = PUBLIC_KEY_MIN_LENGTH, max = PUBLIC_KEY_MAX_LENGTH)
  @Email
  private String email;

  @NotEmpty
  @Length(min = PUBLIC_KEY_MIN_LENGTH, max = PUBLIC_KEY_MAX_LENGTH)
  @Pattern(regexp = PUBLIC_KEY_REGEX)
  private String userKey;

  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String password;

  @NotNull
  @Size(min = SECRET_MIN_LENGTH, max = SECRET_MAX_LENGTH)
  private String confirm;

  public CreateAccountReq() {
  }

  public CreateAccountReq(final String nickname, final String email, final String userKey, final String password,
      final String confirm) {
    this.nickname = nickname;
    this.email = email;
    this.userKey = userKey;
    this.password = password;
    this.confirm = confirm;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(final String nickname) {
    this.nickname = nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public String getUserKey() {
    return this.userKey;
  }

  public void setUserKey(final String userKey) {
    this.userKey = userKey;
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

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountReq.class.getSimpleName())
               .append("{nickname=").append(singleQuote(this.nickname))
               .append(", email=").append(singleQuote(this.email))
               .append(", userKey=").append(singleQuote(this.userKey))
               .append(", password='[ PROTECTED ]', confirm='[ PROTECTED ]'}")
               .toString();
  }
}
