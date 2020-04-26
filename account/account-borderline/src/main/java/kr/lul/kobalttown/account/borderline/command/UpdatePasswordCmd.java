package kr.lul.kobalttown.account.borderline.command;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.transfer.account.UserCmd;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/02/02
 */
public class UpdatePasswordCmd extends UserCmd {
  private byte[] current;
  private byte[] password;

  public UpdatePasswordCmd(final TimestampedContext context, final long user, final String current, final String password) {
    this(context.getId(), user, current, password, context.getTimestamp());
  }

  public UpdatePasswordCmd(final UUID id, final long user, final String current, final String password, final Instant timestamp) {
    super(id, user, timestamp);
    notEmpty(current, "current");
    notEmpty(password, "password");

    this.current = current.getBytes(UTF_8);
    this.password = password.getBytes(UTF_8);
  }

  public String getCurrent() {
    return new String(this.current, UTF_8);
  }

  public String getPassword() {
    return new String(this.password, UTF_8);
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
    return Arrays.equals(this.current, that.current) &&
               Arrays.equals(this.password, that.password);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + Arrays.hashCode(this.current);
    result = 31 * result + Arrays.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    return format("{id=%s, user=%d, current=[ PROTECTED ], password=[ PROTECTED ], timestamp=%s}",
        this.id, this.user, this.timestamp);
  }
}
