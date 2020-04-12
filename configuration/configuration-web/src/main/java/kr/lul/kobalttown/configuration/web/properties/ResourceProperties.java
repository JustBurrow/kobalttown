package kr.lul.kobalttown.configuration.web.properties;

import java.io.File;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2020/04/12
 */
public class ResourceProperties {
  /**
   * URL path pattern. Ant pattern.
   */
  private String path;
  private File location;
  private boolean cache = true;

  public String getPath() {
    return this.path;
  }

  public void setPath(final String path) {
    this.path = path;
  }

  public File getLocation() {
    return this.location;
  }

  public void setLocation(final File location) {
    this.location = location;
  }

  public boolean isCache() {
    return this.cache;
  }

  public void setCache(final boolean cache) {
    this.cache = cache;
  }

  @Override
  public String toString() {
    return format("{path=%s, location=%s, cache=%b}", this.path, this.location, this.cache);
  }
}
