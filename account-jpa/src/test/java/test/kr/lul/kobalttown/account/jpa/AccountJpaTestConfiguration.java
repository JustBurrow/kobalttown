package test.kr.lul.kobalttown.account.jpa;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationJpaConfiguration.class, AccountJpaConfiguration.class})
@EnableJpaRepositories(basePackages = ConfigurationJpaConfiguration.JPA_BASE_SCAN_PACKAGE)
@EntityScan(basePackages = ConfigurationJpaConfiguration.JPA_BASE_SCAN_PACKAGE)
public class AccountJpaTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccountJpaTestUtil accountEntityUtil() {
    return new AccountJpaTestUtil();
  }
}