package kr.lul.kobalttown.account.dto;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class AbstractAccountDto {
  protected long id;
  protected String nickname;

  public AbstractAccountDto() {
  }

  public AbstractAccountDto(long id, String nickname) {
    this.id = id;
    this.nickname = nickname;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}
