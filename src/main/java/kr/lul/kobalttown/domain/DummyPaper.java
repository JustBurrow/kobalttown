package kr.lul.kobalttown.domain;

import java.nio.file.Path;
import java.util.Objects;
import java.util.StringJoiner;

import static kr.lul.common.util.Texts.singleQuote;

/**
 * 테스트용 더미 페이퍼.
 *
 * @author justburrow
 * @since 2019-02-10
 */
@Deprecated(forRemoval = true)
public class DummyPaper implements Paper {
  private Path path;
  private String theme;

  private String description;

  public DummyPaper(Path path, String theme) {
    this(path, theme, "");
  }

  public DummyPaper(Path path, String theme, String description) {
    this.path = path;
    this.theme = theme;

    setDescription(description);
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.domain.Paper
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public int getId() {
    return 0;
  }

  @Override
  public Path getPath() {
    return this.path;
  }

  @Override
  public String getTheme() {
    return this.theme;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DummyPaper)) {
      return false;
    }
    DummyPaper that = (DummyPaper) o;
    return Objects.equals(this.path, that.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.path);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DummyPaper.class.getSimpleName() + "[", "]")
        .add("path=" + this.path)
        .add("theme=" + singleQuote(this.theme))
        .add("description=" + singleQuote(this.description))
        .toString();
  }
}