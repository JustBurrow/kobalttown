package kr.lul.kobalttown.document.dto;

import kr.lul.kobalttown.account.dto.AbstractAccountDto;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public abstract class AbstractNoteDto<A extends AbstractAccountDto> {
  protected long id;
  protected A author;
  protected String body;

  public AbstractNoteDto() {
  }

  public AbstractNoteDto(final long id, final A author, final String body) {
    setId(id);
    setAuthor(author);
    setBody(body);
  }

  public long getId() {
    return this.id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public A getAuthor() {
    return this.author;
  }

  public void setAuthor(final A author) {
    this.author = author;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(final String body) {
    this.body = body;
  }
}
