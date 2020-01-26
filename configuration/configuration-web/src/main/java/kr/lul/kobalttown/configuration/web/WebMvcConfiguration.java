package kr.lul.kobalttown.configuration.web;

import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.page.root.GlobalMvc.V;
import kr.lul.kobalttown.page.root.RootMvc;
import kr.lul.support.spring.web.context.ContextService;
import kr.lul.support.spring.web.context.DefaultContextService;
import kr.lul.support.spring.web.controller.CommonWebHttpExceptionHandler;
import kr.lul.support.spring.web.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
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

  @Bean
  public CommonWebHttpExceptionHandler commonWebHttpExceptionHandler() {
    return new CommonWebHttpExceptionHandler() {
      @Override
      public String notFound(final NotFound e, final WebRequest request, final Model model) {
        log.info("#notFound args : e={}, request={}, model={}", e, request, model);
        return V.ERROR_404;
      }
    };
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // org.springframework.web.servlet.config.annotation.WebMvcConfigurer
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
