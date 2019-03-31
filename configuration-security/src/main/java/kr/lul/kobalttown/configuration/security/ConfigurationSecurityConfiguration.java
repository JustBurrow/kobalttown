package kr.lul.kobalttown.configuration.security;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.support.spring.security.AccountDetailsService;
import kr.lul.kobalttown.support.spring.security.SecretHashEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
@ComponentScan(basePackageClasses = {ConfigurationJpaConfiguration.class, AccountJpaConfiguration.class})
public class ConfigurationSecurityConfiguration {
  @Autowired
  private CredentialRepository credentialRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecretHashEncoder secretHashEncoder() {
    return new SecretHashEncoder(passwordEncoder());
  }

  @Bean
  public AccountDetailsService accountDetailsService() {
    return new AccountDetailsServiceImpl(this.credentialRepository);
  }
}