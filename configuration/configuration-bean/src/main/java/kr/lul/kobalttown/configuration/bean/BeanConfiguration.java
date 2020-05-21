package kr.lul.kobalttown.configuration.bean;

import kr.lul.common.util.SystemMillisTimeProvider;
import kr.lul.common.util.TimeProvider;
import kr.lul.support.spring.common.context.ContextService;
import kr.lul.support.spring.common.context.DefaultContextService;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Map;
import java.util.concurrent.Executor;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/14
 */
@Configuration
@EnableConfigurationProperties
public class BeanConfiguration {
  private static final Logger log = getLogger(BeanConfiguration.class);

  public static final String MDC_PROP_CONTEXT = "ctx";

  @Bean
  public TimeProvider timeProvider() {
    final TimeProvider timeProvider = new SystemMillisTimeProvider();
    log.info("#timeProvider now={}, zoneId={}", timeProvider.now(), timeProvider.zoneId());
    return timeProvider;
  }

  @Bean
  public ContextService contextService() {
    return new DefaultContextService();
  }

  @Bean
  public TaskDecorator taskDecorator() {
    class MdcDecorator implements TaskDecorator {
      private final Logger log = getLogger(MdcDecorator.class);

      @Override
      public Runnable decorate(final Runnable runnable) {
        if (this.log.isTraceEnabled())
          this.log.trace("#decorate args : runnable={}", runnable);

        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
          this.log.info("#run thread={}", Thread.currentThread());

          try {
            if (null == contextMap) {
              if (this.log.isTraceEnabled())
                this.log.trace("#run clear MDC context.");
              MDC.clear();
            } else {
              if (!contextMap.containsKey(MDC_PROP_CONTEXT))
                this.log.info("#run MDC context has no {}.", MDC_PROP_CONTEXT);
              if (this.log.isTraceEnabled())
                this.log.trace("#run set MDC context map : {}", contextMap);
              MDC.setContextMap(contextMap);
            }
          } finally {
            if (this.log.isTraceEnabled())
              this.log.trace("#decorate clear MDC context.");
            MDC.clear();
          }
        };
      }
    }

    return new MdcDecorator();
  }

  @Bean
  public Executor executor() {
    return new ThreadPoolTaskExecutor();
  }
}
