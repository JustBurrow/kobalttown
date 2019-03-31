package kr.lul.kobalttown.account.dto;

import java.time.ZonedDateTime;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public class SummaryAccountDto {
  private int id;
  private String nickname;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public SummaryAccountDto() {
  }

  public SummaryAccountDto(int id, String nickname, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
    this.id = id;
    this.nickname = nickname;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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


  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (0 >= this.id || !(o instanceof SummaryAccountDto)) {
      return false;
    }

    return this.id == ((SummaryAccountDto) o).id;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("{id=").append(this.id)
        .append(", nickname='").append(this.nickname).append('\'')
        .append(", createdAt=").append(this.createdAt)
        .append(", updatedAt=").append(this.updatedAt)
        .append('}').toString();
  }
}