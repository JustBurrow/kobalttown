package kr.lul.kobalttown.common.util;

import java.time.Instant;
import java.time.ZoneId;

/**
 * @author justburrow
 * @since 2019-04-23
 */
public class MillisSystemTimeProvider implements TimeProvider {
  @Override
  public ZoneId zoneId() {
    return ZoneId.systemDefault();
  }

  @Override
  public Instant now() {
    return Instant.ofEpochMilli(System.currentTimeMillis());
  }
}