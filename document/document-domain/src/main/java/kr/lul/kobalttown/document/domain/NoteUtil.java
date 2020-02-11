package kr.lul.kobalttown.document.domain;

import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.common.util.Arguments.notNegative;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public abstract class NoteUtil {
  private static final ThreadLocalRandom random = ThreadLocalRandom.current();

  public static String body() {
    return body(random.nextInt(100));
  }

  public static String body(final int length) {
    notNegative(length, "length");

    return random(length);
  }

  protected NoteUtil() {
    throw new UnsupportedOperationException();
  }
}
