package kr.lul.kobalttown.common.util;

import java.time.Instant;
import java.time.ZoneId;

/**
 * 시스템 기본 설정을 따르는 시각 정보를 제공한다.
 *
 * @author justburrow
 * @since 2018. 9. 17.
 */
public class SystemTimeProvider implements TimeProvider {
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.commons.util.TimeProvider
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public ZoneId zoneId() {
    return ZoneId.systemDefault();
  }

  @Override
  public Instant now() {
    return Instant.now();
  }
}