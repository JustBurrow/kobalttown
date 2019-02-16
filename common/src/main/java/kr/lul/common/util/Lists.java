package kr.lul.common.util;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * {@link List} 유틸리티.
 *
 * @author justburrow
 * @since 2019-01-04
 */
public abstract class Lists {
  /**
   * 배열을 변경 가능한 리스트로 변환한다.
   *
   * @param elements 배열.
   * @param <E>      요소의 타입.
   *
   * @return 변경 가능한 리스트.
   */
  public static <E> List<E> mutable(E... elements) {
    List<E> list = new ArrayList<>();
    for (E element : elements) {
      list.add(element);
    }

    return list;
  }

  /**
   * 배열을 변경 불가능한 리스트로 변환한다.
   *
   * @param elements 나머지 요소.
   * @param <E>      요소의 타입.
   *
   * @return 변경 불가능한 리스트.
   */
  public static <E> List<E> immutable(E... elements) {
    List<E> list = new ArrayList<>();
    for (E element : elements) {
      list.add(element);
    }

    return unmodifiableList(list);
  }

  public Lists() {
    throw new UnsupportedOperationException();
  }
}