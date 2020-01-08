package kr.lul.kobalttown.account.domain;

/**
 * 계정 인증 코드의 상태 변경 에러.
 *
 * @author justburrow
 * @since 2020/01/07
 */
public class ValidationCodeStatusException extends IllegalStateException {
  private ValidationCode.Status current;
  private ValidationCode.Status target;

  public ValidationCodeStatusException(final ValidationCode.Status current, final ValidationCode.Status target) {
    this.current = current;
    this.target = target;
  }

  public ValidationCodeStatusException(final ValidationCode.Status current, final ValidationCode.Status target,
      final String message) {
    super(message);
    this.current = current;
    this.target = target;
  }

  public ValidationCode.Status getCurrent() {
    return this.current;
  }

  public ValidationCode.Status getTarget() {
    return this.target;
  }
}
