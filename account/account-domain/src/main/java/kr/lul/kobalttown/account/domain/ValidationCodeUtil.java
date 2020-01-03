package kr.lul.kobalttown.account.domain;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.ValidationCode.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public abstract class ValidationCodeUtil {
  public static String code() {
    return randomAlphanumeric(CODE_LENGTH);
  }

  public static Duration ttl() {
    return Duration.of(current().nextLong(TTL_MIN.toMillis(), TTL_MAX.toMillis() + 1L), MILLIS);
  }

  protected ValidationCodeUtil() {
    throw new UnsupportedOperationException();
  }
}
