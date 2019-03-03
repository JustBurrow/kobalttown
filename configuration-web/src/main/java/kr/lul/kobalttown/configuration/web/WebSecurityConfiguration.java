package kr.lul.kobalttown.configuration.web;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.configuration.security.ConfigurationSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.security.AccountDetailsServiceImpl;
import kr.lul.kobalttown.support.spring.security.AccountDetailsService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
@ComponentScan(basePackageClasses = {ConfigurationWebConfiguration.class, ConfigurationSecurityConfiguration.class,
    AccountJpaConfiguration.class})
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger log = getLogger(WebSecurityConfiguration.class);

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private CredentialRepository credentialRepository;

  @Bean
  public AccountDetailsService accountDetailsService() {
    return new AccountDetailsServiceImpl(this.credentialRepository);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/");
    http.logout()
        .logoutSuccessUrl("/");

    http.authorizeRequests()
        .antMatchers("/", "/**").permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(accountDetailsService())
        .passwordEncoder(this.passwordEncoder);
  }
}