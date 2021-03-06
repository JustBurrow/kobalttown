package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.kobalttown.configuration.mail.MailConfigurer;
import kr.lul.support.spring.common.context.ContextService;
import kr.lul.support.spring.common.context.DefaultContextService;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication
@Import({BeanConfiguration.class, MailConfigurer.class, JpaConfiguration.class})
public class AccountBorderlineTestConfiguration {
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
