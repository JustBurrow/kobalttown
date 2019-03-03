package kr.lul.kobalttown.common.util;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public class AssertionException extends RuntimeException {
  public AssertionException() {
  }

  public AssertionException(String message) {
    super(message);
  }

  public AssertionException(String message, Throwable cause) {
    super(message, cause);
  }

  public AssertionException(Throwable cause) {
    super(cause);
  }
}