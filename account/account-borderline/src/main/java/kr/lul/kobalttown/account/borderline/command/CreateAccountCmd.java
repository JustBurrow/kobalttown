package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/05
 */
public class CreateAccountCmd extends ContextContainer {
  private String nickname;
  private String email;
  private byte[] password;
  private Instant timestamp;

  public CreateAccountCmd(Context context, String nickname, String email, String password, Instant timestamp) {
    super(context);
    notEmpty(nickname, "nickname");
    notEmpty(email, "email");
    notEmpty(password, "password");
    notNull(timestamp, "timestamp");

    this.nickname = nickname;
    this.email = email;
    this.password = password.getBytes(UTF_8);
    this.timestamp = timestamp;
  }

  public String getNickname() {
    return this.nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return new String(this.password, UTF_8);
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    CreateAccountCmd that = (CreateAccountCmd) o;
    return this.context.equals(that.context) &&
        this.nickname.equals(that.nickname) &&
        this.email.equals(that.email) &&
        Arrays.equals(this.password, that.password);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(this.context, this.nickname, this.email);
    result = 31 * result + Arrays.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountCmd.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", nickname=").append(singleQuote(this.nickname))
        .append(", email=").append(singleQuote(this.email))
        .append(", password=[ PROTECTED ], timestamp=").append(this.timestamp)
        .append('}').toString();
  }
}
