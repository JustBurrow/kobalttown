package kr.lul.kobalttown.article.domain;

/**
 * 글 작성중에 특정 필드 때문에 발생한 에러.
 *
 * @author justburrow
 * @since 2019-05-03
 */
public class CreateArticleFieldException extends CreateArticleException {
  /**
   * 에러의 원인 필드.
   * 필드가 원인이 아닐 겅우엔 {@code null}.
   */
  private String field;

  public CreateArticleFieldException(String field) {
    this.field = field;
  }

  public CreateArticleFieldException(String field, String message) {
    super(message);
    this.field = field;
  }

  public CreateArticleFieldException(String field, String message, Throwable cause) {
    super(message, cause);
    this.field = field;
  }

  public CreateArticleFieldException(String field, Throwable cause) {
    super(cause);
    this.field = field;
  }

  public String getField() {
    return this.field;
  }
}