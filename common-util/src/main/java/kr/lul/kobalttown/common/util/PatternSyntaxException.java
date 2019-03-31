package kr.lul.kobalttown.common.util;

/**
 * @author justburrow
 * @since 2019-02-27
 */
public class PatternSyntaxException extends RuntimeException {
  public PatternSyntaxException() {
  }

  public PatternSyntaxException(String message) {
    super(message);
  }

  public PatternSyntaxException(String message, Throwable cause) {
    super(message, cause);
  }

  public PatternSyntaxException(Throwable cause) {
    super(cause);
  }
}