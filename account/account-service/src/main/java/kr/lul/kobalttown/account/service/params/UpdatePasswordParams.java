package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.transfer.account.UserParams;

import java.util.Arrays;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;

/**
 * @author justburrow
 * @since 2020/02/05
 */
public class UpdatePasswordParams extends UserParams {
  private byte[] current;
  private byte[] password;

  public UpdatePasswordParams(final TimestampedContext context, final Account user, final String current, final String password) {
    super(context, user);
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
    final UpdatePasswordParams that = (UpdatePasswordParams) o;
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
    return format("{id=%s, user=%s, current=[ PROTECTED ], password=[ PROTECTED ], timestamp=%s}",
        this.id, this.user.toSimpleString(), this.timestamp);
  }
}
