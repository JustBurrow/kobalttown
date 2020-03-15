package kr.lul.kobalttown.document.domain;

import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

/**
 * {@link Note}를 수정할 수 있는 인터페이스를 제공한다.
 *
 * @author justburrow
 * @see Note
 * @see NoteSnapshot
 * @since 2020/03/04
 */
public interface NoteUpdater {
  /**
   * @return 노트 ID.
   *
   * @see Note#getId()
   */
  long getId();

  /**
   * @return 노트 버전.
   *
   * @see Note#getVersion()
   */
  int getVersion();

  /**
   * @return 노트 작성자.
   *
   * @see Note#getAuthor()
   */
  Account getAuthor();

  /**
   * @return 내용.
   *
   * @see Note#getBody()
   */
  String getBody();

  /**
   * @param body 내용.
   *
   * @see Note#getBody()
   */
  void setBody(String body);

  /**
   * @return 노트 등록 시각.
   *
   * @see Note#getCreatedAt()
   */
  Instant getCreatedAt();

  /**
   * @return 마지막 노트 수정 시각.
   *
   * @see Note#getUpdatedAt()
   */
  Instant getUpdatedAt();
}
