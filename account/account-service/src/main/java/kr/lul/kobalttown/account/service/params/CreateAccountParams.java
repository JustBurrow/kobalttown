package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.UuidContext;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class CreateAccountParams {
  private UuidContext context;
  private String nickname;
  private String email;
  private String password;
  private Instant instant;

  public CreateAccountParams(UuidContext context, String nickname, String email, String password,
      Instant instant) {
    this.context = context;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.instant = instant;
  }

  public UuidContext getContext() {
    return this.context;
  }

  public String getNickname() {
    return this.nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public Instant getInstant() {
    return this.instant;
  }

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountParams.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", nickname='").append(this.nickname).append('\'')
        .append(", email='").append(this.email).append('\'')
        .append(", password='").append(this.password).append('\'')
        .append(", instant=").append(this.instant)
        .append('}')
        .toString();
  }
}
