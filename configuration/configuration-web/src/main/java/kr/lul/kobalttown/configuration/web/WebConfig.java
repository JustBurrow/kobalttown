package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.configuration.web.properties.WebProperties;
import org.slf4j.Logger;

import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/04/12
 */
public class WebConfig {
  protected static final Logger log = getLogger(WebConfig.class);

  private Map<String, ResourceConfig> resources;
  private MessageConfig message;

  public WebConfig(final WebProperties properties) {
    notNull(properties, "properties");

    this.resources = properties.getResources()
                         .stream()
                         .map(prop -> new ResourceConfig(prop.getPath(), prop.getLocation(), prop.isCache()))
                         .collect(toUnmodifiableMap(ResourceConfig::getPath, resourceConfig -> resourceConfig));
    this.message = new MessageConfig(properties.getMessage());
  }

  public Map<String, ResourceConfig> getResources() {
    return this.resources;
  }

  public MessageConfig getMessage() {
    return this.message;
  }

  @Override
  public String toString() {
    return format("{resources=%s, message=%s}", this.resources, this.message);
  }
}
