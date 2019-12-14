package kr.lul.kobalttown.account.web;

import kr.lul.common.util.SystemTimeProvider;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import kr.lul.support.spring.web.context.ContextService;
import kr.lul.support.spring.web.context.DefaultContextService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = AccountWebAnchor.class)
@Import({JpaConfiguration.class})
public class AccountWebTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  @ConditionalOnMissingBean
  public ContextService contextService() {
    return new DefaultContextService();
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityEncoder securityEncoder() {
    return new PasswordEncoderSecurityEncoder(new BCryptPasswordEncoder());
  }
}
