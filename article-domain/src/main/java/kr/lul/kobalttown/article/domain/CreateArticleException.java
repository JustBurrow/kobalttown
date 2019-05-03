package kr.lul.kobalttown.article.domain;

/**
 * 글 작성중에 발생한 에러.
 *
 * @author justburrow
 * @since 2019-05-03
 */
public class CreateArticleException extends Exception {
  public CreateArticleException() {
  }

  public CreateArticleException(String message) {
    super(message);
  }

  public CreateArticleException(String message, Throwable cause) {
    super(message, cause);
  }

  public CreateArticleException(Throwable cause) {
    super(cause);
  }
}