package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Creatable;

import java.io.Serializable;
import java.time.Instant;

/**
 * 도큐먼트의 특정 버전.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Snapshot extends Creatable<Instant> {
  interface Id extends Serializable {
    Class<?> type();

    long document();

    int revision();
  }

  Id getId();

  /**
   * @return 스냅샷의 버전. 0-based.
   */
  int getRevision();

  String getKey();

  /**
   * @return 대상 도큐먼트.
   */
  Document getDocument();
}
