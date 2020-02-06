package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Savable;

import java.time.Instant;

/**
 * 기본 자료형.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Document extends Savable<Instant> {
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
  History getHistory(int size, int page);
}
