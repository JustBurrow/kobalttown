package test.configuration;

import kr.lul.kobalttown.account.borderline.AccountBorderlineConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.security.ConfigurationSecurityConfiguration;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author justburrow
 * @since 2019-05-04
 */
@SpringBootApplication(scanBasePackageClasses = {AccountBorderlineConfiguration.class,
    ConfigurationSecurityConfiguration.class, ConfigurationJpaConfiguration.class})
public class AccountBorderlineTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public AccountServiceTestUtil accountServiceTestUtil() {
    return new AccountServiceTestUtil();
  }
}