package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountError {
  public static final String PREFIX = "error.account";

  public static final String CREATE_CONFIRM_NOT_MATCH = PREFIX + ".create.confirm-not-match";

  public AccountError() {
    throw new UnsupportedOperationException();
  }
}
