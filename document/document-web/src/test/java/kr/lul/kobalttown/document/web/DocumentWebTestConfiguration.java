package kr.lul.kobalttown.document.web;

import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.mail.MailConfigurer;
import kr.lul.kobalttown.document.test.DocumentTestAnchor;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import kr.lul.support.spring.web.context.ContextService;
import kr.lul.support.spring.web.context.DefaultContextService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@SpringBootApplication(scanBasePackageClasses = {DocumentWebAnchor.class, DocumentTestAnchor.class})
@Import({BeanConfiguration.class, MailConfigurer.class})
public class DocumentWebTestConfiguration {
  @Bean
  @ConditionalOnMissingBean
  public JavaMailSender javaMailSender() {
    return new JavaMailSenderImpl();
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
