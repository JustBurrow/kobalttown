package kr.lul.kobalttown.configuration.security;

import kr.lul.kobalttown.page.root.RootMvc;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/25
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private static final Logger log = getLogger(WebSecurityConfiguration.class);

  @Autowired
  private ApplicationContext applicationContext;

  @PostConstruct
  private void postConstruct() {
    log.info("applicationContext is autowired : {}", this.applicationContext);
  }

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
  protected void configure(final HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage(RootMvc.C.LOG_IN)
        .usernameParameter(RootMvc.M.USERNAME)
        .passwordParameter(RootMvc.M.PASSWORD)
        .loginProcessingUrl(RootMvc.C.LOG_IN)
        .defaultSuccessUrl(RootMvc.C.INDEX);

    http.logout()
        .logoutUrl(RootMvc.C.LOG_OUT)
        .logoutSuccessUrl(RootMvc.C.INDEX);

    http.authorizeRequests()
        .antMatchers(RootMvc.C.INDEX).permitAll()
        .antMatchers(RootMvc.C.LOG_IN).anonymous()
        .antMatchers(RootMvc.C.LOG_OUT).authenticated();
  }

  @Override
  protected UserDetailsService userDetailsService() {
    final UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    this.applicationContext.getAutowireCapableBeanFactory().autowireBean(userDetailsService);
    return userDetailsService;
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService())
        .passwordEncoder(passwordEncoder());
  }
}
