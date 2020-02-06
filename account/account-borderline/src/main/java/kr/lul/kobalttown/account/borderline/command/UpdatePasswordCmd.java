package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.Context;
import kr.lul.common.data.ContextContainer;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2020/02/02
 */
public class UpdatePasswordCmd extends ContextContainer {
  private long user;
  private byte[] current;
  private byte[] password;
  private Instant timestamp;

  public UpdatePasswordCmd(final Context context, final long user, final String current, final String password,
      final Instant timestamp) {
    super(context);
    positive(user, "user");
    notEmpty(current, "current");
    notEmpty(password, "password");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.current = current.getBytes(UTF_8);
    this.password = password.getBytes(UTF_8);
    this.timestamp = timestamp;
  }

  public long getUser() {
    return this.user;
  }

  public String getCurrent() {
    return new String(this.current, UTF_8);
  }

  public String getPassword() {
    return new String(this.password, UTF_8);
  }

  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    final UpdatePasswordCmd that = (UpdatePasswordCmd) o;
    return this.user == that.user &&
               Arrays.equals(this.current, that.current) &&
               Arrays.equals(this.password, that.password) &&
               this.timestamp.equals(that.timestamp);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(super.hashCode(), this.user, this.timestamp);
    result = 31 * result + Arrays.hashCode(this.current);
    result = 31 * result + Arrays.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    return format("{context=%s, user=%d, current=[ PROTECTED ], password=[ PROTECTED ], timestamp=%s}",
        this.context, this.user, this.timestamp);
  }
}
