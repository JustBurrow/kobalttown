package kr.lul.kobalttown.common.util;

import java.time.*;

/**
 * 시각 정보의 중앙 관리용 인터페이스.
 * 테스트 등에서 특정 시각에 대한 테스트를 진행할 필요가 있을 경우 등,
 * 애플리케이션 전체의 시각 정보를
 *
 * @author justburrow
 * @since 2018. 9. 17.
 */
public interface TimeProvider {
  /**
   * 기준 시간대.
   * 시간대에 의존적인 기능을 사용하기 위한 기반 기능.
   *
   * @return 시간대 ID.
   */
  ZoneId zoneId();

  /**
   * @return 기준 시간대의 {@link ZoneOffset}.
   */
  default ZoneOffset zoneOffset() {
    return zoneId().getRules().getOffset(now());
  }

  /**
   * 나노세컨드 정밀도의 현재 시각.
   *
   * @return 현재시각.
   */
  Instant now();

  /**
   * @return 기준 시간대의 현재 {@link ZonedDateTime}.
   */
  default ZonedDateTime zonedDateTime() {
    return now().atZone(zoneId());
  }

  /**
   * @return 기준 시간대의 현재 {@link OffsetDateTime}.
   */
  default OffsetDateTime offsetDateTime() {
    return OffsetDateTime.ofInstant(now(), zoneId());
  }

  /**
   * @return 기준 시간대의 현재 {@link OffsetTime}.
   */
  default OffsetTime offsetTime() {
    return OffsetTime.ofInstant(now(), zoneId());
  }

  /**
   * @return 기준 시간대의 현재 {@link LocalDateTime}.
   */
  default LocalDateTime localDateTime() {
    return LocalDateTime.ofInstant(now(), zoneId());
  }

  /**
   * @return 기준 시간대의 현재 {@link LocalDate}.
   */
  default LocalDate localDate() {
    return LocalDate.ofInstant(now(), zoneId());
  }

  /**
   * @return 기준 시간대의 현재 {@link LocalTime}.
   */
  default LocalTime localTime() {
    return LocalTime.ofInstant(now(), zoneId());
  }

  default ZonedDateTime zonedDateTime(Instant instant) {
    return ZonedDateTime.ofInstant(instant, zoneId());
  }
}
