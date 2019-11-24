package kr.lul.kobalttown.account.dto;

import kr.lul.common.util.Texts;

import java.time.ZonedDateTime;

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

  public AccountDetailDto(long id, String nickname, boolean enabled, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
    super(id, nickname);
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return new StringBuilder(AccountDetailDto.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", nickname=").append(Texts.singleQuote(this.nickname))
        .append(", enabled=").append(this.enabled)
        .append(", createdAt=").append(this.createdAt)
        .append(", updatedAt=").append(this.updatedAt)
        .append('}')
        .toString();
  }
}
