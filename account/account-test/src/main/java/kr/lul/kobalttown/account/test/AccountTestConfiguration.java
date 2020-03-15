package kr.lul.kobalttown.account.test;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2020/02/08
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountDataAnchor.class})
@Import({BeanConfiguration.class, JpaConfiguration.class})
public class AccountTestConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityEncoder securityEncoder() {
    return new PasswordEncoderSecurityEncoder(passwordEncoder());
  }
}
