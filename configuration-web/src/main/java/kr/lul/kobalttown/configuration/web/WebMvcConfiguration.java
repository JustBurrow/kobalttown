package kr.lul.kobalttown.configuration.web;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
  private static final Logger log = getLogger(WebMvcConfiguration.class);

  @Bean
  public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    HiddenHttpMethodFilter filter = new HiddenHttpMethodFilter();

    if (log.isTraceEnabled()) {
      log.trace("return : {}", filter);
    }
    return filter;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // org.springframework.web.servlet.config.annotation.WebMvcConfigurer
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("page/_/login");

    if (log.isTraceEnabled()) {
      log.trace("result : registry={}", registry);
    }
  }
}