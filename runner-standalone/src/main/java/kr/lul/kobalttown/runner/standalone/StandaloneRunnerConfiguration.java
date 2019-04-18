package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-19
 */
@Configuration
public class StandaloneRunnerConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}