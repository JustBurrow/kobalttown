package kr.lul.kobalttown.document.domain;

import java.util.List;

/**
 * 도큐먼트의 변경 이력.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface History<D extends Document<?>, S extends Snapshot<D, ?>> {
  int size();

  /**
   * @return 0-based.
   */
  int page();

  /**
   * @return 전체 스냅샷 갯수.
   */
  int totalSize();

  /**
   * @return 페이지 수. 1-based.
   */
  int totalPage();

  /**
   * @return 현재 이력이 첫 페이지이면 {@code true}.
   */
  default boolean isFirst() {
    return 0 == page();
  }

  /**
   * @return 현재 이력이 마지막 페이지이면 {@code true}.
   */
  default boolean isLast() {
    return page() == totalPage() - 1;
  }

  /**
   * @return 현재 이력의 스냅샷.
   */
  List<S> content();
}
