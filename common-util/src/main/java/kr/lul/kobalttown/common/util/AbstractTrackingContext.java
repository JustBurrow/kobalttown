package kr.lul.kobalttown.common.util;

import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class AbstractTrackingContext<ID> implements TrackingContext<ID> {
  protected final ID trackingId;
  protected final Instant timestamp;
  private final Instant timestampMillis;

  public AbstractTrackingContext(ID trackingId, Instant timestamp) {
    notNull(trackingId, "trackingId");
    notNull(timestamp, "timestamp");

    this.trackingId = trackingId;
    this.timestamp = timestamp;
    this.timestampMillis = Instant.ofEpochMilli(timestamp.toEpochMilli());
  }

  public Instant getTimestampMillis() {
    return this.timestampMillis;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.util.TrackingContext
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public ID getTrackingId() {
    return this.trackingId;
  }

  @Override
  public Instant getTimestamp() {
    return this.timestamp;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return format("trackingId=%s, timestamp=%s, timestampMillis=%s",
        this.trackingId, this.timestamp, this.timestampMillis);
  }
}