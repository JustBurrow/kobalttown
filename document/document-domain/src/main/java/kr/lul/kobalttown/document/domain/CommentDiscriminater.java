package kr.lul.kobalttown.document.domain;

/**
 * 댓글 종류.
 *
 * @author justburrow
 * @since 2020/02/06
 */
public enum CommentDiscriminater {
  COMMENT("기본 댓글"),
  RECOMMENT("대댓글");

  private String description;

  CommentDiscriminater(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}
