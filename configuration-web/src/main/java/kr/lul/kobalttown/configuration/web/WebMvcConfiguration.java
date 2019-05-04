package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.configuration.web.support.AccountDetailsHandlerMethodArgumentResolverImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import java.util.List;

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

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
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

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(new AccountDetailsHandlerMethodArgumentResolverImpl());

    if (log.isTraceEnabled()) {
      log.trace("result : resolvers={}", resolvers);
    }
  }
}