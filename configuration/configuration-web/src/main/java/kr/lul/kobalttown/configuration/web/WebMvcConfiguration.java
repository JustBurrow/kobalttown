package kr.lul.kobalttown.configuration.web;

import com.github.bufferings.thymeleaf.extras.nl2br.dialect.Nl2brDialect;
import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.configuration.web.properties.WebProperties;
import kr.lul.kobalttown.page.root.GlobalMvc.V;
import kr.lul.kobalttown.page.root.RootMvc;
import kr.lul.support.spring.web.context.ContextService;
import kr.lul.support.spring.web.context.DefaultContextService;
import kr.lul.support.spring.web.controller.CommonWebHttpExceptionHandler;
import kr.lul.support.spring.web.controller.SpringWebExceptionHandler;
import kr.lul.support.spring.web.interceptor.LoggingInterceptor;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.CacheControl.noCache;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
  protected static final Logger log = getLogger(WebMvcConfiguration.class);

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

  @Bean
  public SpringWebExceptionHandler springWebExceptionHandler() {
    return new SpringWebExceptionHandler();
  }

  @Bean
  public Nl2brDialect dialect() {
    return new Nl2brDialect();
  }

  @Bean
  @ConfigurationProperties("kr.lul.kobalttown.front.web")
  public WebProperties webProperties() {
    return new WebProperties();
  }

  @Bean
  public WebConfig webConfig() {
    final WebProperties properties = webProperties();
    log.info("#webConfig properties={}", properties);

    final WebConfig config = new WebConfig(properties);
    log.info("#webConfig return : {}", config);
    return config;
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

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    webConfig().getResources().forEach((pattern, config) -> {
      if (log.isDebugEnabled())
        log.debug("#addResourceHandlers config={}", config);

      final ResourceHandlerRegistration registration =
          registry.addResourceHandler(pattern)
              .addResourceLocations(config.getLocation().toString());
      if (!config.isCache())
        registration.setCacheControl(noCache());
    });
    log.info("#addResourceHandlers registry={}", registry);
  }
}
