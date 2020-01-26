package kr.lul.kobalttown.account.domain;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Deprecated
public class UsedValidationCodeException extends IllegalStateException {
  private Instant usedAt;

  public UsedValidationCodeException(final Instant usedAt) {
    super("already used at " + usedAt);
    this.usedAt = usedAt;
  }

  public Instant getUsedAt() {
    return this.usedAt;
  }
}
