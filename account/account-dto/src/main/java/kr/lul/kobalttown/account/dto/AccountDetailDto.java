package kr.lul.kobalttown.account.dto;

import java.time.ZonedDateTime;

import static java.util.Objects.hash;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class AccountDetailDto extends AbstractAccountDto {
  private boolean enabled;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public AccountDetailDto() {
  }

  public AccountDetailDto(final long id, final String nickname, final boolean enabled, final ZonedDateTime createdAt,
      final ZonedDateTime updatedAt) {
    super(id, nickname);
    notNull(createdAt, "createdAt");
    notNull(updatedAt, "updatedAt");

    this.enabled = enabled;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final AccountDetailDto that = (AccountDetailDto) o;
    return this.id == that.id &&
        this.nickname.equals(that.nickname) &&
        this.enabled == that.enabled &&
        this.createdAt.equals(that.createdAt) &&
        this.updatedAt.equals(that.updatedAt);
  }

  @Override
  public int hashCode() {
    return hash(this.id, this.nickname, this.enabled, this.createdAt, this.updatedAt);
  }

  @Override
  public String toString() {
    return new StringBuilder(AccountDetailDto.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", nickname=").append(singleQuote(this.nickname))
        .append(", enabled=").append(this.enabled)
        .append(", createdAt=").append(this.createdAt)
        .append(", updatedAt=").append(this.updatedAt)
        .append('}').toString();
  }
}
