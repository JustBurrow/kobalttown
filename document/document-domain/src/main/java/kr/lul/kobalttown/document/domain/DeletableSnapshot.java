package kr.lul.kobalttown.document.domain;

import java.time.Instant;

/**
 * 삭제 가능한 스냅샷.
 *
 * @author justburrow
 * @since 2020/03/21
 */
public interface DeletableSnapshot extends Snapshot {
  /**
   * @return 삭제된 시각. 삭제하지 않았으면 {@code null}.
   */
  Instant getDeleted();

  /**
   * @return 삭제되었으면 {@code true}.
   */
  default boolean isDeleted() {
    return null != getDeleted();
  }

  /**
   * 스냅샷 삭제하기.
   *
   * @param deletedAt 삭제한 시각.
   *
   * @throws IllegalStateException 이미 삭제되었을 경우.
   */
  void delete(Instant deletedAt) throws IllegalStateException;
}
