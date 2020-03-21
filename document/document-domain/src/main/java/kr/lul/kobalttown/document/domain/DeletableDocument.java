package kr.lul.kobalttown.document.domain;

import java.time.Instant;

/**
 * 삭제 가능한 도큐먼트.
 *
 * @author justburrow
 * @since 2020/03/21
 */
public interface DeletableDocument extends Document {
  /**
   * @return 삭제된 시각. 삭제되지 않았으면 {@code null}.
   */
  Instant getDeletedAt();

  /**
   * @return 삭제된 도큐먼트이면 {@code true}.
   */
  default boolean isDeleted() {
    return null != getDeletedAt();
  }

  /**
   * 도큐먼트 삭제하기.
   *
   * @param deletedAt 삭제 시각.
   *
   * @throws IllegalStateException 이미 삭제된 도큐먼트일 때.
   */
  void delete(Instant deletedAt) throws IllegalStateException;
}
