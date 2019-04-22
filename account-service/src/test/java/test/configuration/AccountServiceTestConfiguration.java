package test.configuration;

import kr.lul.kobalttown.account.service.AccountServiceConfiguration;
import kr.lul.kobalttown.common.util.MillisSystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.security.ConfigurationSecurityConfiguration;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@SpringBootApplication(scanBasePackageClasses = {AccountServiceConfiguration.class,
    ConfigurationSecurityConfiguration.class, ConfigurationJpaConfiguration.class})
public class AccountServiceTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new MillisSystemTimeProvider();
  }

  @Bean
  public AccountServiceTestUtil articleServiceTestUtil() {
    return new AccountServiceTestUtil();
  }
}