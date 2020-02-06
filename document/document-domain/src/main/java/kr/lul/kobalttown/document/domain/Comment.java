package kr.lul.kobalttown.document.domain;

/**
 * {@link Document}의 댓글.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Comment {
  long getId();

  String getKey();

  /**
   * @return 대상 도큐먼트.
   */
  Document getDocument();

  /**
   * @return 댓글이 달린 시점의 도큐먼트.
   */
  Snapshot getSnapshot();

  /**
   * @return 댓글 내용.
   */
  String getBody();
}
