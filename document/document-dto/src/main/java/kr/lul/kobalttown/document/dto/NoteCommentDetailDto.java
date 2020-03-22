package kr.lul.kobalttown.document.dto;

import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import java.time.ZonedDateTime;

import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public class NoteCommentDetailDto {
  private long id;
  private AccountSimpleDto author;
  private String body;
  private ZonedDateTime createdAt;

  public NoteCommentDetailDto() {
  }

  public NoteCommentDetailDto(final long id, final AccountSimpleDto author, final String body, final ZonedDateTime createdAt) {
    setId(id);
    setAuthor(author);
    setBody(body);
    setCreatedAt(createdAt);
  }

  public long getId() {
    return this.id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public AccountSimpleDto getAuthor() {
    return this.author;
  }

  public void setAuthor(final AccountSimpleDto author) {
    this.author = author;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(final String body) {
    this.body = body;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return new StringBuilder()
               .append("{id=").append(this.id)
               .append(", author=").append(this.author)
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", createdAt=").append(this.createdAt)
               .append('}').toString();
  }
}
