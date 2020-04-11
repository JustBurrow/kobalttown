package kr.lul.kobalttown.document.dto;

import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import java.time.ZonedDateTime;
import java.util.List;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class NoteDetailDto extends AbstractNoteDto<AccountSimpleDto> {
  private int version;
  private List<NoteCommentDetailDto> comments;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public NoteDetailDto() {
  }

  public NoteDetailDto(final long id, final int version, final AccountSimpleDto author, final String body,
      final List<NoteCommentDetailDto> comments, final ZonedDateTime createdAt, final ZonedDateTime updatedAt) {
    super(id, author, body);
    setVersion(version);
    setComments(comments);
    setCreatedAt(createdAt);
    setUpdatedAt(updatedAt);
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  public List<NoteCommentDetailDto> getComments() {
    return this.comments;
  }

  public void setComments(final List<NoteCommentDetailDto> comments) {
    this.comments = comments;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ZonedDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(final ZonedDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public String toString() {
    return format("{id=%d, version=%d, author=%s, body=%s, comments=%s createdAt=%s, updatedAt=%s}",
        this.id, this.version, this.author, singleQuote(this.body), this.comments, this.createdAt, this.updatedAt);
  }
}
