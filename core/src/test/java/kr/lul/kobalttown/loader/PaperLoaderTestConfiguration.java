package kr.lul.kobalttown.loader;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-10
 */
@SpringBootApplication
public class PaperLoaderTestConfiguration {
  private static final Logger log = getLogger(PaperLoaderTestConfiguration.class);

  @Bean
  public PaperLoaderDelegator paperLoaderDelegator() {
    PaperLoaderDelegatorImpl delegator = new PaperLoaderDelegatorImpl();
    delegator.init();

    if (log.isTraceEnabled()) {
      log.trace("return : {}", delegator);
    }
    return delegator;
  }
}