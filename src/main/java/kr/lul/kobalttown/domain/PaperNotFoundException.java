package kr.lul.kobalttown.domain;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class PaperNotFoundException extends RuntimeException {
  public PaperNotFoundException() {
  }

  public PaperNotFoundException(String message) {
    super(message);
  }

  public PaperNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PaperNotFoundException(Throwable cause) {
    super(cause);
  }
}