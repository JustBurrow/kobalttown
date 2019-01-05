package kr.lul.kobalttown.domain;

import java.util.StringJoiner;

/**
 * 기본 구현.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public class BasicPaper implements Paper {
  private String theme;

  public void setTheme(String theme) {
    this.theme = theme;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.domain.Paper
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String getThemem() {
    return this.theme;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", BasicPaper.class.getSimpleName() + "[", "]")
        .add("theme='" + this.theme + "'")
        .toString();
  }
}