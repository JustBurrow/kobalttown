package kr.lul.kobalttown.article.web;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

import static kr.lul.kobalttown.common.util.Texts.singleQuote;

/**
 * @author justburrow
 * @since 2019-05-03
 */
public class CreateArticleInput {
  @NotEmpty
  private String title;
  @NotEmpty
  private String body;

  public CreateArticleInput() {
  }

  public CreateArticleInput(String title, String body) {
    this.title = title;
    this.body = body;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", CreateArticleInput.class.getSimpleName() + "[", "]")
        .add("title=" + singleQuote(this.title))
        .add("body=" + singleQuote(this.body))
        .toString();
  }
}