package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@EnableAsync
@ComponentScan(basePackageClasses = AccountDataAnchor.class)
public class AccountServiceConfigurer {
  private static final Logger log = getLogger(AccountServiceConfigurer.class);

  @Bean
  @ConfigurationProperties("kr.lul.kobalttown.account.service")
  public AccountServiceProperties accountServiceProperties() {
    return new AccountServiceProperties();
  }

  @Bean
  public AccountServiceConfiguration accountServiceConfiguration() {
    final AccountServiceProperties properties = accountServiceProperties();
    log.info("#accountServiceConfiguration properties={}", properties);

    final AccountServiceConfiguration configuration = new AccountServiceConfiguration(properties);
    log.info("#accountServiceConfiguration configuration={}", configuration);
    return configuration;
  }

  @Bean
  public ActivationConfiguration activationConfiguration() {
    return accountServiceConfiguration().getActivation();
  }

  /**
   * TODO 공통화.
   */
  @Bean
  @ConditionalOnMissingBean
  public Executor executor() {
    return new ThreadPoolTaskExecutor();
  }
}
