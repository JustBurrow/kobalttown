package kr.lul.kobalttown.domain;

import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Texts.singleQuote;

/**
 * 범용 페이퍼.
 *
 * @author justburrow
 * @since 2019-02-10
 */
public class GenericPaper implements Paper {
  public static final String DEFAULT_DESCRIPTION = "Some generic paper.";
  private Path path;
  private String theme;

  private String description;

  private Map<String, Object> kartkaMap;

  public GenericPaper(Path path, String theme) {
    this(path, theme, DEFAULT_DESCRIPTION);
  }

  public GenericPaper(Path path, String theme, String description) {
    notNull(path, "path");
    notEmpty(theme, "theme");

    this.path = path;
    this.theme = theme;
    setDescription(description);
    this.kartkaMap = new LinkedHashMap<>();
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    notNull(description, "description");
    this.description = description;
  }

  public Object addKartka(String name, Object kartka) {
    notEmpty(name, "name");

    return this.kartkaMap.put(name, kartka);
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
  public List<Kartka> getKartkas() {
    return this.kartkaMap.entrySet().stream()
        .map(entry -> new Kartka() {
          @Override
          public String getName() {
            return entry.getKey();
          }

          @Override
          public Object getValue() {
            return entry.getValue();
          }
        })
        .collect(toList());
  }

  @Override
  public Map<String, Object> getAttributes() {
    return this.kartkaMap;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GenericPaper)) {
      return false;
    }
    GenericPaper that = (GenericPaper) o;
    return Objects.equals(this.path, that.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.path);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", GenericPaper.class.getSimpleName() + "[", "]")
        .add("path=" + this.path)
        .add("theme=" + singleQuote(this.theme))
        .add("description=" + singleQuote(this.description))
        .add("kartkaMap=" + this.kartkaMap)
        .toString();
  }
}