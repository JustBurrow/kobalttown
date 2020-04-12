package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.configuration.web.properties.ResourceProperties;
import kr.lul.kobalttown.configuration.web.properties.WebProperties;
import org.slf4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableMap;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/04/12
 */
public class WebConfig {
  protected static final Logger log = getLogger(WebConfig.class);

  private Map<String, ResourceConfig> resources;

  public WebConfig(final WebProperties properties) {
    notNull(properties, "properties");

    initResources(properties.getResources());
  }

  private void initResources(final List<ResourceProperties> properties) {
    notNull(properties, "properties");

    this.resources = new HashMap<>();
    properties.forEach(prop -> {
      final String path = prop.getPath();
      if (this.resources.containsKey(path))
        log.warn(format("#initResources path duplicated : %s", prop));

      final File location = prop.getLocation();

      this.resources.put(path, new ResourceConfig(path, location, prop.isCache()));
    });
  }

  public Map<String, ResourceConfig> getResources() {
    return unmodifiableMap(this.resources);
  }

  @Override
  public String toString() {
    return format("{resources=%s}", this.resources);
  }
}
