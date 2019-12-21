package kr.lul.kobalttown.configuration.bean;

import kr.lul.common.util.SystemMillisTimeProvider;
import kr.lul.common.util.TimeProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author justburrow
 * @since 2019/12/14
 */
@Configuration
public class BeanConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemMillisTimeProvider();
  }

  @Bean
  public Executor executor() {
    return new ThreadPoolTaskExecutor();
  }
}
