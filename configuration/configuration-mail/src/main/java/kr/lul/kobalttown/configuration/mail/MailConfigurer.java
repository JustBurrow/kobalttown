package kr.lul.kobalttown.configuration.mail;

import kr.lul.support.spring.mail.MailService;
import kr.lul.support.spring.mail.MailServiceImpl;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/21
 */
@Configuration
public class MailConfigurer {
  private static final Logger log = getLogger(MailConfigurer.class);

  @Bean
  public MailService mailService() {
    return new MailServiceImpl();
  }
}
