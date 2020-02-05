package kr.lul.kobalttown.account.service.params;

import kr.lul.common.data.ContextContainer;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2020/02/05
 */
public class UpdatePasswordParams extends ContextContainer {
  private Account user;
  private byte[] current;
  private byte[] password;
  private Instant timestamp;

  public UpdatePasswordParams(final ContextContainer container, final Account user, final String current, final String password,
      final Instant timestamp) {
    this(container, user, current.getBytes(UTF_8), password.getBytes(UTF_8), timestamp);
  }

  public UpdatePasswordParams(final ContextContainer container, final Account user, final byte[] current, final byte[] password,
      final Instant timestamp) {
    super(container);
    notNull(user, "user");
    notEmpty(current, "current");
    notEmpty(password, "password");
    notNull(timestamp, "timestamp");

    this.user = user;
    this.current = current;
    this.password = password;
    this.timestamp = timestamp;
  }

  public Account getUser() {
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
    final UpdatePasswordParams that = (UpdatePasswordParams) o;
    return this.user.equals(that.user) &&
               this.context.equals(that.context) &&
               Arrays.equals(this.current, that.current) &&
               Arrays.equals(this.password, that.password) &&
               this.timestamp.equals(this.timestamp);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(this.context, this.user, this.timestamp);
    result = 31 * result + Arrays.hashCode(this.current);
    result = 31 * result + Arrays.hashCode(this.password);
    return result;
  }

  @Override
  public String toString() {
    return format("{context=%s, user=%s, current=[ PROTECTED ], password=[ PROTECTED ], timestamp=%s}",
        this.context, this.user.toSimpleString(), this.timestamp);
  }
}
