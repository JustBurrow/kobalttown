package kr.lul.kobalttown.account.domain;

/**
 * 계정 인증 코드의 상태 변경 에러.
 *
 * @author justburrow
 * @since 2020/01/07
 */
public class EnableCodeStatusException extends IllegalStateException {
  private EnableCode.Status current;
  private EnableCode.Status target;

  public EnableCodeStatusException(final EnableCode.Status current, final EnableCode.Status target) {
    this.current = current;
    this.target = target;
  }

  public EnableCodeStatusException(final EnableCode.Status current, final EnableCode.Status target, final String message) {
    super(message);
    this.current = current;
    this.target = target;
  }

  public EnableCode.Status getCurrent() {
    return this.current;
  }

  public EnableCode.Status getTarget() {
    return this.target;
  }
}
