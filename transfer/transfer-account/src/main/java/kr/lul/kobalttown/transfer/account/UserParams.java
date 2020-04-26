package kr.lul.kobalttown.transfer.account;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;

/**
 * 유저가 실행 하는 {@code *.service} 레이어용.
 *
 * @author justburrow
 * @since 2020/04/26
 */
public class UserParams extends TimestampedContext {
  /**
   * 실행한 유저.
   */
  protected Account user;

  public UserParams(final Account user) {
    this(randomUUID(), user, now());
  }

  public UserParams(final Context context, final Account user, final Instant timestamp) {
    this(context.getId(), user, timestamp);
  }

  public UserParams(final TimestampedContext context, final Account user) {
    this(context.getId(), user, context.getTimestamp());
  }

  public UserParams(final UUID id, final Account user, final Instant timestamp) {
    super(id, timestamp);
    notNull(user, "user");
    positive(user.getId(), "user.id");

    this.user = user;
  }

  public Account getUser() {
    return this.user;
  }
}
