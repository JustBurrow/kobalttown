package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.page.root.RootMvc;
import kr.lul.support.spring.web.context.ContextService;
import kr.lul.support.spring.web.context.DefaultContextService;
import kr.lul.support.spring.web.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
  @Bean
  public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new HiddenHttpMethodFilter();
  }

  @Bean
  public LoggingInterceptor loggingInterceptor() {
    return new LoggingInterceptor();
  }

  @Bean
  public ContextService contextService() {
    return new DefaultContextService();
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(loggingInterceptor());
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController(RootMvc.C.LOG_IN)
        .setViewName(RootMvc.V.LOG_IN);
    registry.addViewController(RootMvc.C.INDEX)
        .setViewName(RootMvc.V.INDEX);
  }
}
