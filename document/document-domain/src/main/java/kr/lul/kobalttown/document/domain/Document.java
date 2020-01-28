package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Savable;

import java.time.Instant;

/**
 * 기본 자료형.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Document<D extends Document<?>> extends Savable<Instant> {
  long getId();

  /**
   * @return 현재 버전. 0-based.
   */
  int getVersion();

  String getKey();

  /**
   * @param size 한 페이지에 들어갈 스냅샷 갯수.
   * @param page 0-based.
   *
   * @return
   */
  History<D> getHistory(int size, int page);

  default History<D> getHistory(final int page) {
    return getHistory(100, page);
  }

  default History<D> getHistory() {
    return getHistory(0);
  }
}
