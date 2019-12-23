package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;
import org.slf4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

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
  public static final String PROPERTIES_ACCOUNT_SERVICE = "kr.lul.kobalttown.account.service";

  @Bean
  @ConfigurationProperties(PROPERTIES_ACCOUNT_SERVICE)
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
  public ActivateCodeConfiguration activateCodeConfiguration() {
    return accountServiceConfiguration().getActivateCode();
  }
}
