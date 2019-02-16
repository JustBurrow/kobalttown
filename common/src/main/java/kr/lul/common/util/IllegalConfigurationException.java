package kr.lul.common.util;

/**
 * @author justburrow
 * @since 2019-02-16
 */
public class IllegalConfigurationException extends RuntimeException {
  public IllegalConfigurationException() {
  }

  public IllegalConfigurationException(String message) {
    super(message);
  }

  public IllegalConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalConfigurationException(Throwable cause) {
    super(cause);
  }
}