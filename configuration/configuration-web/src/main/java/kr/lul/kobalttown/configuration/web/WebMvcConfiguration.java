package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.page.root.RootMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
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

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController(RootMvc.C.LOG_IN)
        .setViewName(RootMvc.V.LOG_IN);
  }
}
