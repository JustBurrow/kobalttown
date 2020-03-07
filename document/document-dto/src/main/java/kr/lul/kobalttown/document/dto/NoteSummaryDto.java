package kr.lul.kobalttown.document.dto;

import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import java.time.ZonedDateTime;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class NoteSummaryDto extends AbstractNoteDto<AccountSimpleDto> {
  private int version;
  private ZonedDateTime createdAt;

  public NoteSummaryDto() {
  }

  public NoteSummaryDto(final long id, final int version, final AccountSimpleDto author, final String body,
      final ZonedDateTime createdAt) {
    super(id, author, body);
    setVersion(version);
    setCreatedAt(createdAt);
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(final int version) {
    this.version = version;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(final ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return format("{id=%d, version=%d, author=%s, body=%s, createdAt=%s}",
        this.id, this.version, this.author, singleQuote(this.body), this.createdAt);
  }
}
