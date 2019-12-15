package kr.lul.kobalttown.account.dto;

import java.util.Objects;

import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class AbstractAccountDto {
  protected long id;
  protected String nickname;

  public AbstractAccountDto() {
  }

  public AbstractAccountDto(final long id, final String nickname) {
    notNull(nickname, "nickname");

    this.id = id;
    this.nickname = nickname;
  }

  public long getId() {
    return this.id;
  }

  public String getNickname() {
    return this.nickname;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final AbstractAccountDto that = (AbstractAccountDto) o;
    return this.id == that.id &&
        this.nickname.equals(that.nickname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.nickname);
  }
}
