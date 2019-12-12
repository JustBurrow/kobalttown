package kr.lul.kobalttown.configuration.bean;

import kr.lul.common.util.SystemTimeProvider;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.bean.context.ContextService;
import kr.lul.kobalttown.configuration.bean.context.DefaultContextService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/11/25
 */
@Configuration
public class BeanConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public ContextService contextService() {
    return new DefaultContextService();
  }
}
