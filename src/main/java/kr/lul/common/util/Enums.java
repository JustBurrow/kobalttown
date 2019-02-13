package kr.lul.common.util;

import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.shuffle;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Lists.mutable;

/**
 * {@link Enum enum} 유틸리티.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public abstract class Enums {
  /**
   * {@code enum}의 값 중에서 임의로 하나를 반환한다.
   *
   * @param clz {@code enum} 클래스.
   * @param <E> {@link Enum} 클래스.
   *
   * @return 임의의 값.
   *
   * @see Enum#valueOf(Class, String)
   */
  public static <E extends Enum> E random(Class<E> clz) {
    notNull(clz, "clz");

    List<E> values = mutable(clz.getEnumConstants());
    if (values.isEmpty()) {
      throw new IllegalArgumentException(format("enum class has no constant : %s", clz));
    }
    shuffle(values);

    return values.get(0);
  }

  public Enums() {
    throw new UnsupportedOperationException();
  }
}