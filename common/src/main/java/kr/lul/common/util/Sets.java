package kr.lul.common.util;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.unmodifiableSet;

/**
 * {@link java.util.Set} 유틸리티.
 *
 * @author justburrow
 * @since 2019-01-04
 */
public abstract class Sets {
  /**
   * 배열을 변경한 셋으로 변환한다.
   *
   * @param elements 배열.
   * @param <E>      배열 요소의 타입.
   *
   * @return 변경 가능한 셋.
   */
  public static <E> Set<E> mutable(E... elements) {
    Set<E> set = new HashSet<>();
    for (E e : elements) {
      set.add(e);
    }

    return set;
  }

  /**
   * 배열을 변경 불가능한 셋으로 변환한다.
   *
   * @param elements 배열.
   * @param <E>      배열 요소의 타입.
   *
   * @return 변경 불가능한 셋.
   */
  public static <E> Set<E> immutable(E... elements) {
    Set<E> set = new HashSet<>();
    for (E e : elements) {
      set.add(e);
    }

    return unmodifiableSet(set);
  }

  public Sets() {
    throw new UnsupportedOperationException();
  }
}