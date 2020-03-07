package kr.lul.kobalttown.account.dto;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class AccountSimpleDto extends AbstractAccountDto {
  public AccountSimpleDto() {
  }

  public AccountSimpleDto(final long id, final String nickname) {
    super(id, nickname);
  }

  @Override
  public String toString() {
    return format("{id=%d, nickname=%s}", this.id, singleQuote(this.nickname));
  }
}
