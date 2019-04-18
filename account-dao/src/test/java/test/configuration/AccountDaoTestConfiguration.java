package test.configuration;

import kr.lul.kobalttown.account.dao.AccountDaoConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.AccountDaoTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-10
 */
@SpringBootApplication(scanBasePackageClasses = {AccountDaoConfiguration.class,
    ConfigurationJpaConfiguration.class})
public class AccountDaoTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccountDaoTestUtil accountDaoTestUtil() {
    return new AccountDaoTestUtil();
  }
}