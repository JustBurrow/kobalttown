package kr.lul.kobalttown.article.dto;

import kr.lul.kobalttown.account.dto.SimpleAccountDto;

import java.time.ZonedDateTime;
import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-04-24
 */
public class DetailArticleDto {
  private long id;
  private String title;
  private String summary;
  private String body;
  private SimpleAccountDto author;
  private ZonedDateTime createdAt;

  public DetailArticleDto() {
  }

  public DetailArticleDto(long id, String title, String summary, String body,
      SimpleAccountDto author, ZonedDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.summary = summary;
    this.body = body;
    this.author = author;
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

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public SimpleAccountDto getAuthor() {
    return this.author;
  }

  public void setAuthor(SimpleAccountDto author) {
    this.author = author;
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
    return new StringJoiner(", ", DetailArticleDto.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("title=" + singleQuote(this.title))
        .add("summary=" + singleQuote(this.summary))
        .add("body=" + singleQuote(this.body))
        .add("author=" + this.author)
        .add("createdAt=" + this.createdAt)
        .toString();
  }
}