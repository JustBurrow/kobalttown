package kr.lul.kobalttown.configuration.web;

import kr.lul.common.util.IllegalConfigurationException;

import java.io.File;
import java.net.URI;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2020/04/12
 */
public class ResourceConfig {
  private String path;
  private File location;
  private boolean cache;

  public ResourceConfig() {
  }

  public ResourceConfig(final String path, final File location, final boolean cache) {
    notNull(path, "path");
    notNull(location, "location");

    if (null == path)
      throw new IllegalConfigurationException("path is null : " + path);
    else if (path.isEmpty())
      throw new IllegalConfigurationException("path is empty : " + path);
    else
      this.path = path;

    if (null == location)
      throw new IllegalConfigurationException("location is null : " + location);
    else if (!location.exists())
      throw new IllegalConfigurationException("location does not exist : " + location);
    else if (!location.canRead())
      throw new IllegalConfigurationException("location is not readable : " + location);
    else
      this.location = location;

    this.cache = cache;
  }

  public String getPath() {
    return this.path;
  }

  public URI getLocation() {
    return this.location.toURI();
  }

  public boolean isCache() {
    return this.cache;
  }

  @Override
  public String toString() {
    return format("{path=%s, location=%s, cache=%b}", this.path, this.location, this.cache);
  }
}
