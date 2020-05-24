package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.AnonymousCmd;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/12/05
 */
public class CreateAccountCmd extends AnonymousCmd {
  private String nickname;
  private String email;
  private String userKey;
  private byte[] password;

  public CreateAccountCmd(final TimestampedContext context, final String nickname, final String email, final String userKey,
      final String password) {
    this(context, nickname, email, userKey, password, context.getTimestamp());
  }

  public CreateAccountCmd(final Context context, final String nickname, final String email, final String userKey,
      final String password, final Instant timestamp) {
    super(context.getId(), timestamp);
    notEmpty(nickname, "nickname");
    notEmpty(email, "email");
    notEmpty(userKey, "userKey");
    notEmpty(password, "password");

    this.nickname = nickname;
    this.email = email;
    this.userKey = userKey;
    this.password = password.getBytes(UTF_8);
  }

  public String getNickname() {
    return this.nickname;
  }

  public String getEmail() {
    return this.email;
  }

  public String getUserKey() {
    return this.userKey;
  }

  public String getPassword() {
    return new String(this.password, UTF_8);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    final CreateAccountCmd that = (CreateAccountCmd) o;
    return this.nickname.equals(that.nickname) &&
               this.userKey.equals(this.userKey) &&
               this.email.equals(that.email) &&
               Arrays.equals(this.password, that.password);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), this.nickname, this.email, this.userKey);
    result = 31 * result + Arrays.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", nickname=").append(singleQuote(this.nickname))
               .append(", email=").append(singleQuote(this.email))
               .append(", userKey=").append(singleQuote(this.userKey))
               .append(", password=[ PROTECTED ], timestamp=").append(this.timestamp)
               .append('}').toString();
  }
}
