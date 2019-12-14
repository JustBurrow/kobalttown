package kr.lul.kobalttown.configuration.bean;

import kr.lul.common.util.SystemTimeProvider;
import kr.lul.common.util.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/12/14
 */
@Configuration
public class BeanConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}
