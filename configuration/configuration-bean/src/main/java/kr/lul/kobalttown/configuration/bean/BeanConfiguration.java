package kr.lul.kobalttown.configuration.bean;

import kr.lul.common.util.SystemMillisTimeProvider;
import kr.lul.common.util.TimeProvider;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/14
 */
@Configuration
public class BeanConfiguration {
  private static final Logger log = getLogger(BeanConfiguration.class);

  @Bean
  public TimeProvider timeProvider() {
    final TimeProvider timeProvider = new SystemMillisTimeProvider();
    log.info("#timeProvider now={}, zoneId={}", timeProvider.now(), timeProvider.zoneId());
    return timeProvider;
  }

  @Bean
  public Executor executor() {
    return new ThreadPoolTaskExecutor();
  }
}
