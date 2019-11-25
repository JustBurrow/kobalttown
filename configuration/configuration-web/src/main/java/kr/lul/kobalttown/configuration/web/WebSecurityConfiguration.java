package kr.lul.kobalttown.configuration.web;

import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019/11/25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityEncoder securityEncoder() {
    return new PasswordEncoderSecurityEncoder(passwordEncoder());
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
  }
}
