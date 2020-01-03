package kr.lul.kobalttown.account.domain;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class ExpiredValidationCodeException extends IllegalStateException {
  private Instant expiredAt;

  public ExpiredValidationCodeException(final Instant expiredAt) {
    super("already expired at " + expiredAt);
    this.expiredAt = expiredAt;
  }

  public Instant getExpiredAt() {
    return this.expiredAt;
  }
}
