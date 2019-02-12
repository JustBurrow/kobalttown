package kr.lul.kobalttown.domain;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
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
  private Map<String, Attribute> attributeMap;

  public DummyPaper(Path path, String theme) {
    this(path, theme, "");
  }

  public DummyPaper(Path path, String theme, String description) {
    this(path, theme, description, new HashMap<>());
  }

  public <V, K> DummyPaper(Path path, String theme, String description, HashMap<String, Attribute> attributeMap) {
    notNull(path, "path");
    notEmpty(theme, "theme");
    notNull(attributeMap, "attributeMap");

    this.path = path;
    this.theme = theme;
    setDescription(description);
    this.attributeMap = attributeMap;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    notNull(description, "description");
    this.description = description;
  }

  public Attribute addAttribute(String name, Attribute attribute) {
    notEmpty(name, "name");

    return this.attributeMap.put(name, attribute);
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

  @Override
  public Map<String, Attribute> getAttributeMap() {
    return this.attributeMap;
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
        .add("attributeMap=" + this.attributeMap)
        .toString();
  }
}