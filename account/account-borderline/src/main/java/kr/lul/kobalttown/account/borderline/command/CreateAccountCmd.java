package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.UuidContext;

import java.time.Instant;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/05
 */
public class CreateAccountCmd {
  private UuidContext context;
  private String nickname;
  private String email;
  private byte[] password;
  private Instant timestamp;

  public CreateAccountCmd() {
  }

  public CreateAccountCmd(UuidContext context, String nickname, String email, String password, Instant timestamp) {
    notNull(context, "context");
    notNull(timestamp, "timestamp");

    this.context = context;
    setNickname(nickname);
    setEmail(email);
    setPassword(password);
    this.timestamp = timestamp;
  }

  public UuidContext getContext() {
    return this.context;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    notEmpty(nickname, "nickname");
    this.nickname = nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    notEmpty(email, "email");
    this.email = email;
  }

  public String getPassword() {
    return new String(this.password, UTF_8);
  }

  public void setPassword(String password) {
    notEmpty(password, "password");
    this.password = password.getBytes(UTF_8);
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountCmd.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", nickname=").append(singleQuote(this.nickname))
        .append(", email=").append(singleQuote(this.email))
        .append(", password=[ PROTECTED ], timestamp=").append(this.timestamp).toString();
  }
}
