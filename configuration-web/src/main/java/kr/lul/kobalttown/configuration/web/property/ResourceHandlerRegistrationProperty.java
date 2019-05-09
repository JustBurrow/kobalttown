package kr.lul.kobalttown.configuration.web.property;

import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author justburrow
 * @see org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration
 * @since 2019-05-09
 */
public class ResourceHandlerRegistrationProperty {
  private String[] patterns;
  private String[] locations;

  public ResourceHandlerRegistrationProperty() {
  }

  public String[] getPatterns() {
    return this.patterns;
  }

  public void setPatterns(String[] patterns) {
    this.patterns = patterns;
  }

  public String[] getLocations() {
    return this.locations;
  }

  public void setLocations(String[] locations) {
    this.locations = locations;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", "{", "}")
        .add("patterns=" + Arrays.toString(this.patterns))
        .add("locations=" + Arrays.toString(this.locations))
        .toString();
  }
}