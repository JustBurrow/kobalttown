package kr.lul.kobalttown.configuration.web.property;

import org.springframework.beans.factory.annotation.Configurable;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author justburrow
 * @since 2019-05-09
 */
@Configurable
public class ConfigurationWebProperties {
  private List<ResourceHandlerRegistrationProperty> resources;

  public ConfigurationWebProperties() {
  }

  public List<ResourceHandlerRegistrationProperty> getResources() {
    return this.resources;
  }

  public void setResources(
      List<ResourceHandlerRegistrationProperty> resources) {
    this.resources = resources;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringJoiner(", ", ConfigurationWebProperties.class.getSimpleName() + "[", "]")
        .add("resources=" + this.resources)
        .toString();
  }
}