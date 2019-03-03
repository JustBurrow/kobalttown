package kr.lul.kobalttown.common.util;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public interface TrackingContext<ID> {
  ID getTrackingId();

  Instant getTimestamp();
}