package kr.lul.kobalttown.common.api;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class ApiException extends Exception {
  public ApiException() {
  }

  public ApiException(String message) {
    super(message);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}