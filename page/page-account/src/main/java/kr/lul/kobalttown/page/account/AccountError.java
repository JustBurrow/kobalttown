package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountError {
  public static final String PREFIX = "error.account";

  public static final String CREATE_CONFIRM_NOT_MATCH = PREFIX + ".create.confirm-not-match";
  public static final String ISSUE_VALIDATE_CODE_FAIL = PREFIX + ".validate-issue.fail";

  public AccountError() {
    throw new UnsupportedOperationException();
  }
}
