package kr.lul.kobalttown.document.dto;

import kr.lul.common.data.Pagination;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import java.time.ZonedDateTime;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class NoteDetailDto extends AbstractNoteDto<AccountSimpleDto> {
  public static final String PROP_COMMENTS_PAGE = "comments.page";
  public static final String PROP_COMMENTS_SIZE = "comments.size";
  public static final int DEFAULT_COMMENTS_PAGE = 0;
  public static final int DEFAULT_COMMENTS_SIZE = 20;

  private int version;
  private Pagination<NoteCommentDetailDto> comments;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;

  public NoteDetailDto() {
  }

  public NoteDetailDto(final long id, final int version, final AccountSimpleDto author, final String body,
      final Pagination<NoteCommentDetailDto> comments, final ZonedDateTime createdAt, final ZonedDateTime updatedAt) {
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

  public Pagination<NoteCommentDetailDto> getComments() {
    return this.comments;
  }

  public void setComments(final Pagination<NoteCommentDetailDto> comments) {
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
