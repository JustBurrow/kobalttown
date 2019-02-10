package kr.lul.kobalttown.configuration;

import kr.lul.kobalttown.loader.InMemoryVerbPathPaperLoader;
import kr.lul.kobalttown.loader.PaperLoaderDelegator;
import kr.lul.kobalttown.loader.PaperLoaderDelegatorImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-10
 */
@Configuration
public class TestBeanConfiguration {
  private static final Logger log = getLogger(TestBeanConfiguration.class);

  @Bean
  public InMemoryVerbPathPaperLoader inMemoryVerbPathPaperLoader() {
    InMemoryVerbPathPaperLoader memory = new InMemoryVerbPathPaperLoader();

    if (log.isTraceEnabled()) {
      log.trace("return : {}", memory);
    }
    return memory;
  }

  @Bean
  public PaperLoaderDelegator paperLoaderDelegator() {
    PaperLoaderDelegatorImpl delegator = new PaperLoaderDelegatorImpl();
    delegator.init(inMemoryVerbPathPaperLoader());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", delegator);
    }
    return delegator;
  }
}