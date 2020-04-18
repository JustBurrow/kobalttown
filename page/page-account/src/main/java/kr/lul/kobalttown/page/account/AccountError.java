package kr.lul.kobalttown.page.account;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public abstract class AccountError {
  public static final String PREFIX = "error.account";

  public static final String CREATE_UNKNOWN = PREFIX + ".create.unknown";
  public static final String CREATE_CONFIRM_NOT_MATCH = PREFIX + ".create.confirm-not-match";
  public static final String CREATE_USED_NICKNAME = PREFIX + ".create.used-nickname";
  public static final String CREATE_USED_EMAIL = PREFIX + ".create.used-email";
  public static final String CREATE_USED_USER_KEY = PREFIX + ".create.used-user-key";
  public static final String ISSUE_ENABLE_CODE_FAIL = PREFIX + ".enable-code.issue.fail";

  public static final String UPDATE_CONFIRM_NOT_MATCH = PREFIX + ".update.confirm-not-match";
  public static final String UPDATE_PASSWORD_NOT_UPDATED = PREFIX + ".update.password-not-updated";

  public AccountError() {
    throw new UnsupportedOperationException();
  }
}
