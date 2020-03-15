package kr.lul.kobalttown.document.dto;

import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class NoteSimpleDto extends AbstractNoteDto<AccountSimpleDto> {
  public NoteSimpleDto() {
  }

  public NoteSimpleDto(final long id, final AccountSimpleDto author, final String body) {
    super(id, author, body);
  }

  @Override
  public String toString() {
    return format("{id=%d, author=%s, body=%s}", this.id, this.author, singleQuote(this.body));
  }
}
