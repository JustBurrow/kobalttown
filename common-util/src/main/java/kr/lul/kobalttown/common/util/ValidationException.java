package kr.lul.kobalttown.common.util;

import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * 검증 실패.
 *
 * @author justburrow
 * @since 2019-05-18
 */
public class ValidationException extends RuntimeException {
  private List<String> target;

  public ValidationException(String target) {
    this.target = List.of(target);
  }

  public ValidationException(List<String> target) {
    this.target = unmodifiableList(target);
  }

  public ValidationException(String target, String message) {
    super(message);
    this.target = List.of(target);
  }

  public ValidationException(List<String> target, String message) {
    super(message);
    this.target = unmodifiableList(target);
  }

  public ValidationException(List<String> target, String message, Throwable cause) {
    super(message, cause);
    this.target = unmodifiableList(target);
  }

  public ValidationException(List<String> target, Throwable cause) {
    super(cause);
    this.target = unmodifiableList(target);
  }

  /**
   * @return 검증 대상에 대한 정보. 오브젝트 변수명, 필드 이름 등등.
   */
  public List<String> getTarget() {
    return this.target;
  }
}