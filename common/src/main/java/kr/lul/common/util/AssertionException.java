package kr.lul.common.util;

/**
 * 단정 실패.
 *
 * @author justburrow
 * @since 2018. 9. 17.
 */
public class AssertionException extends RuntimeException {
  public AssertionException(String message) {
    super(message);
  }

  public AssertionException(String message, Throwable cause) {
    super(message, cause);
  }
}
