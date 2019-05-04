package kr.lul.kobalttown.article.dto;

import java.time.ZonedDateTime;
import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-04-24
 */
public class SummaryArticleDto {
  private long id;
  private String title;
  private String summary;
  private ZonedDateTime createdAt;

  public SummaryArticleDto() {
  }

  public SummaryArticleDto(long id, String title, String summary, ZonedDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.summary = summary;
    this.createdAt = createdAt;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSummary() {
    return this.summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public ZonedDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(ZonedDateTime createdAt) {
    this.createdAt = createdAt;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", SummaryArticleDto.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("title=" + singleQuote(this.title))
        .add("summary=" + singleQuote(this.summary))
        .add("createdAt=" + this.createdAt)
        .toString();
  }
}