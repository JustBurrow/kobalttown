package kr.lul.kobalttown.account.service.configuration;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.account.service.properties.AccountServiceProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private AccountServiceProperties accountServiceProperties;

  @Bean
  public AccountServiceConfig accountServiceConfig() {
    log.info("#accountServiceConfiguration accountServiceProperties={}", this.accountServiceProperties);

    final AccountServiceConfig configuration = new AccountServiceConfig(this.accountServiceProperties);
    log.info("#accountServiceConfiguration configuration={}", configuration);
    return configuration;
  }

  @Bean
  public WelcomeConfig welcomeConfig() {
    final WelcomeConfig configuration = accountServiceConfig().getWelcome();
    log.info("#welcomeConfiguration configuration={}", configuration);
    return configuration;
  }

  @Bean
  public EnableCodeConfig enableCodeConfig() {
    final EnableCodeConfig configuration = accountServiceConfig().getValidationCode();
    log.info("#enableCodeConfiguration configuration={}", configuration);
    return configuration;
  }
}
