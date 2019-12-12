package kr.lul.kobalttown.configuration.security;

import kr.lul.kobalttown.page.root.RootMvc;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
        .loginPage(RootMvc.C.LOG_IN)
        .usernameParameter(RootMvc.M.USERNAME)
        .passwordParameter(RootMvc.M.PASSWORD)
        .loginProcessingUrl(RootMvc.C.LOG_IN)
        .defaultSuccessUrl(RootMvc.C.ROOT);

    http.logout()
        .logoutUrl(RootMvc.C.LOG_OUT)
        .logoutSuccessUrl(RootMvc.C.ROOT);

    http.authorizeRequests()
        .antMatchers(RootMvc.C.ROOT).permitAll()
        .antMatchers(RootMvc.C.LOG_IN).anonymous()
        .antMatchers(RootMvc.C.LOG_OUT).authenticated();
  }

  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService());
  }
}
