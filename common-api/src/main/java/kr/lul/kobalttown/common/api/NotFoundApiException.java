package kr.lul.kobalttown.common.api;

/**
 * @author justburrow
 * @since 2019-05-04
 */
public class NotFoundApiException extends ApiException {
  private Object resourceId;

  public NotFoundApiException(Object resourceId) {
    this.resourceId = resourceId;
  }

  public NotFoundApiException(Object resourceId, String message) {
    super(message);
    this.resourceId = resourceId;
  }

  public NotFoundApiException(Object resourceId, String message, Throwable cause) {
    super(message, cause);
    this.resourceId = resourceId;
  }

  public NotFoundApiException(Object resourceId, Throwable cause) {
    super(cause);
    this.resourceId = resourceId;
  }

  public NotFoundApiException(Object resourceId, String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.resourceId = resourceId;
  }

  public Object getResourceId() {
    return this.resourceId;
  }
}