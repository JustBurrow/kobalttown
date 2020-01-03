package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class CreateAccountParams extends ContextContainer {
  private String nickname;
  private String email;
  private byte[] password;
  private Instant timestamp;

  public CreateAccountParams(final Context context, final String nickname, final String email, final String password,
      final Instant timestamp) {
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
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    final CreateAccountParams that = (CreateAccountParams) o;
    return this.context.equals(that.context) &&
        this.nickname.equals(that.nickname) &&
        this.email.equals(that.email) &&
        this.password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.context, this.nickname, this.email, this.password);
  }

  @Override
  public String toString() {
    return new StringBuilder(CreateAccountParams.class.getSimpleName())
        .append("{context=").append(this.context)
        .append(", nickname='").append(this.nickname).append('\'')
        .append(", email='").append(this.email).append('\'')
        .append(", password=[ PROTECTED ], timestamp=").append(this.timestamp)
        .append('}').toString();
  }
}
