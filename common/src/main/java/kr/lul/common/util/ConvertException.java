package kr.lul.common.util;

/**
 * 오브젝트의 변환 실패.
 *
 * @author justburrow
 * @see IdentifiableConverter#convert(Object)
 * @since 2018. 9. 18.
 */
public class ConvertException extends RuntimeException {
  public ConvertException() {
  }

  public ConvertException(String message) {
    super(message);
  }

  public ConvertException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConvertException(Throwable cause) {
    super(cause);
  }
}
