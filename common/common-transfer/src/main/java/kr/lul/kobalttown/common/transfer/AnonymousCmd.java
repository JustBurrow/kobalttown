package kr.lul.kobalttown.common.transfer;

import kr.lul.common.data.TimestampedContext;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

/**
 * 익명 유저가 실행 하는 {@code *.borderline} 레이어용.
 *
 * @author justburrow
 * @since 2020/04/26
 */
public class AnonymousCmd extends TimestampedContext {
  public AnonymousCmd() {
    this(randomUUID(), now());
  }

  public AnonymousCmd(final UUID id, final Instant timestamp) {
    super(id, timestamp);
  }

  public AnonymousCmd(final TimestampedContext context) {
    this(context.getId(), context.getTimestamp());
  }
}
