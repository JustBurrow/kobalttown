package kr.lul.kobalttown.common.transfer;

import kr.lul.common.data.TimestampedContext;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

/**
 * 시스템이 실행하는 로직의 {@code *.borderline} 레이어용.
 *
 * @author justburrow
 * @since 2020/04/26
 */
public class SystemCmd extends TimestampedContext {
  public SystemCmd() {
    this(randomUUID(), now());
  }

  public SystemCmd(final UUID id, final Instant timestamp) {
    super(id, timestamp);
  }

  public SystemCmd(final TimestampedContext context) {
    this(context.getId(), context.getTimestamp());
  }
}
