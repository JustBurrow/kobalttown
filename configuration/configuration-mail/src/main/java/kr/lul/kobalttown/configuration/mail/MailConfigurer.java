package kr.lul.kobalttown.configuration.mail;

import kr.lul.support.spring.mail.MailService;
import kr.lul.support.spring.mail.MailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/12/21
 */
@Configuration
public class MailConfigurer {
  @Bean
  public MailService mailService() {
    return new MailServiceImpl();
  }
}
