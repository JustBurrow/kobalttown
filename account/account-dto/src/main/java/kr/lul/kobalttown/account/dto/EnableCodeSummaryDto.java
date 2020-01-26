package kr.lul.kobalttown.account.dto;

import java.time.ZonedDateTime;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.*;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public class EnableCodeSummaryDto {
  private long id;
  private String email;
  private String token;
  private ZonedDateTime expireAt;

  public EnableCodeSummaryDto(final long id, final String email, final String token, final ZonedDateTime expireAt) {
    positive(id, "id");
    notEmpty(email, "email");
    notEmpty(token, "token");
    notNull(expireAt, "expireAt");

    this.id = id;
    this.email = email;
    this.token = token;
    this.expireAt = expireAt;
  }

  public long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public String getToken() {
    return this.token;
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
    final EnableCodeSummaryDto that = (EnableCodeSummaryDto) o;
    return this.id == that.id &&
               this.email.equals(that.email) &&
               this.token.equals(that.token) &&
               this.expireAt.equals(that.expireAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.email, this.token, this.expireAt);
  }

  @Override
  public String toString() {
    return format("%s{id=%d, email='%s', token='%s', expireAt=%s}", EnableCodeSummaryDto.class.getSimpleName(),
        this.id, this.email, this.token, this.expireAt);
  }
}
