package kr.lul.kobalttown.configuration.web.properties;

import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.lang.String.format;

/**
 * 로직에서 직접 사용하지 마시오.
 * {@code kr.lul.kobalttown.configuration.web.WebConfig}를 사용할 것.
 *
 * @author justburrow
 * @since 2020/04/12
 */
@Configuration
public class WebProperties {
  private List<ResourceProperties> resources;

  public List<ResourceProperties> getResources() {
    return this.resources;
  }

  public void setResources(final List<ResourceProperties> resources) {
    this.resources = resources;
  }

  @Override
  public String toString() {
    return format("{resources=%s}", this.resources);
  }
}
