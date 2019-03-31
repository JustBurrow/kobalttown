package kr.lul.kobalttown.account.domain;

/**
 * @author justburrow
 * @since 2019-03-16
 */
public class UsedNicknameException extends Exception {
  public UsedNicknameException() {
  }

  public UsedNicknameException(String message) {
    super(message);
  }

  public UsedNicknameException(String message, Throwable cause) {
    super(message, cause);
  }

  public UsedNicknameException(Throwable cause) {
    super(cause);
  }

  public UsedNicknameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}