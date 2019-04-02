package kr.lul.kobalttown.account.jpa;

import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.jpa.AccountEntityUtil;
import kr.lul.kobalttown.test.account.jpa.CredentialEntityUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationJpaConfiguration.class,
    AccountJpaConfiguration.class})
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
  public AccountEntityUtil accountEntityUtil() {
    return new AccountEntityUtil();
  }

  @Bean
  public CredentialEntityUtil credentialEntityUtil() {
    return new CredentialEntityUtil();
  }
}