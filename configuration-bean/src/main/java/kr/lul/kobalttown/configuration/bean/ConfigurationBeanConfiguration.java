package kr.lul.kobalttown.configuration.bean;

import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
public class ConfigurationBeanConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}