package kr.lul.kobalttown.article.dto;

import kr.lul.kobalttown.account.dto.SimpleAccountDto;

import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-04-24
 */
public class SimpleArticleDto {
  private long id;
  private String title;
  private SimpleAccountDto author;

  public SimpleArticleDto() {
  }

  public SimpleArticleDto(long id, String title, SimpleAccountDto author) {
    this.id = id;
    this.title = title;
    this.author = author;
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

  public SimpleAccountDto getAuthor() {
    return this.author;
  }

  public void setAuthor(SimpleAccountDto author) {
    this.author = author;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", SimpleArticleDto.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("title=" + singleQuote(this.title))
        .add("author=" + this.author)
        .toString();
  }
}