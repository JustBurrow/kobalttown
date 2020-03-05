package kr.lul.kobalttown.document.domain;

import java.util.List;

/**
 * 도큐먼트의 변경 이력.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface History<S extends Snapshot> {
  /**
   * @return 인스턴스가 가지고 있는 스냅샷 갯수.
   *
   * @see #content() {@code content().size()}
   */
  default int size() {
    return content().size();
  }

  /**
   * @return 인스턴스가 가질 수 있는 최대 스냅샷 갯수.
   */
  int maxSize();

  /**
   * @return 현재 페이지. 0-based.
   */
  int page();

  /**
   * @return 전체 스냅샷 갯수.
   */
  long totalSize();

  /**
   * @return 페이지 수. 1-based.
   */
  default long totalPage() {
    long temp = totalSize() / maxSize();
    if (0L < totalSize() % maxSize()) {
      temp += 1;
    }
    return temp;
  }

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
   * @return 현재 이력의 스냅샷. unmodifiable.
   */
  List<S> content();
}
