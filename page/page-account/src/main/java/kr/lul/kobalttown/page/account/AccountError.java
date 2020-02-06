package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountError {
  public static final String PREFIX = "error.account";

  public static final String CREATE_CONFIRM_NOT_MATCH = PREFIX + ".create.confirm-not-match";
  public static final String ISSUE_VALIDATE_CODE_FAIL = PREFIX + ".validate-issue.fail";

  public static final String UPDATE_CONFIRM_NOT_MATCH = PREFIX + ".update.confirm-not-match";
  public static final String UPDATE_PASSWORD_NOT_UPDATED = PREFIX + ".update.update-not-updated";

  public AccountError() {
    throw new UnsupportedOperationException();
  }
}
