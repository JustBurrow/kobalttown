package kr.lul.kobalttown.account.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class ValidationCodeSummaryDto {
  private long id;
  private String email;
  private ZonedDateTime expireAt;

  public ValidationCodeSummaryDto(final long id, final String email, final ZonedDateTime expireAt) {
    positive(id, "id");
    notEmpty(email, "email");
    notNull(expireAt, "expireAt");

    this.id = id;
    this.email = email;
    this.expireAt = expireAt;
  }

  public long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public ZonedDateTime getExpireAt() {
    return this.expireAt;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final ValidationCodeSummaryDto that = (ValidationCodeSummaryDto) o;
    return this.id == that.id &&
               this.email.equals(that.email) &&
               this.expireAt.equals(that.expireAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.email, this.expireAt);
  }

  @Override
  public String toString() {
    return format("%s{id=%d, email='%s', expireAt=%s}", ValidationCodeSummaryDto.class.getSimpleName(), this.id, this.email,
        this.expireAt);
  }
}
