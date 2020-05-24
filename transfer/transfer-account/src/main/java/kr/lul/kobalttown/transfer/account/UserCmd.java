package kr.lul.kobalttown.transfer.account;

import kr.lul.common.data.Context;
import kr.lul.common.data.TimestampedContext;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;
import static kr.lul.common.util.Arguments.positive;

/**
 * 유저가 실행 하는 {@code *.borderline} 레이어용.
 *
 * @author justburrow
 * @since 2020/04/26
 */
public class UserCmd extends TimestampedContext {
  /**
   * 실행한 유저의 ID.
   *
   * @see Account#getId()
   */
  protected final long user;

  public UserCmd(final long user) {
    this(randomUUID(), user, now());
  }

  public UserCmd(final Context context, final long user, final Instant timestamp) {
    this(context.getId(), user, timestamp);
  }

  public UserCmd(final TimestampedContext context, final long user) {
    this(context.getId(), user, context.getTimestamp());
  }

  public UserCmd(final UUID id, final long user, final Instant timestamp) {
    super(id, timestamp);

    positive(user, "user");
    this.user = user;
  }

  public long getUser() {
    return this.user;
  }
}
