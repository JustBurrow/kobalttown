package kr.lul.kobalttown.configuration;

import kr.lul.kobalttown.web.controller.WebControllerAnchor;
import kr.lul.kobalttown.web.support.RequestContextResolver;
import org.slf4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-04
 */
@Configuration
@ComponentScan(basePackageClasses = {WebControllerAnchor.class})
public class WebMvcConfiguration implements WebMvcConfigurer {
  private static final Logger log = getLogger(WebMvcConfiguration.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // org.springframework.web.servlet.config.annotation.WebMvcConfigurer
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    if (log.isTraceEnabled()) {
      log.trace("args : resolvers={}", resolvers);
    }

    resolvers.add(new RequestContextResolver());
  }
}