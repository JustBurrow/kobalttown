package kr.lul.kobalttown.account.dto;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public class SimpleAccountDto {
  private int id;
  private String nickname;

  public SimpleAccountDto() {
  }

  public SimpleAccountDto(int id, String nickname) {
    this.id = id;
    this.nickname = nickname;
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

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (0 >= this.id || !(o instanceof SimpleAccountDto)) {
      return false;
    }

    return this.id == ((SimpleAccountDto) o).id;
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
        .append('}').toString();
  }
}