package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.account.web.AccountWebAnchor;
import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.kobalttown.configuration.mail.MailConfigurer;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import kr.lul.kobalttown.document.web.DocumentWebAnchor;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = {AccountWebAnchor.class, DocumentWebAnchor.class})
@Import({BeanConfiguration.class,
    MailConfigurer.class,
    JpaConfiguration.class,
    WebMvcConfiguration.class,
    WebSecurityConfiguration.class})
public class StandaloneRunner {
  private static final Logger log = getLogger(StandaloneRunner.class);

  public static void main(final String... args) {
    final SpringApplication application = new SpringApplication(StandaloneRunner.class);
    application.addListeners(new ApplicationPidFileWriter());
    final ApplicationContext context = application.run(args);

    if (log.isTraceEnabled()) {
      for (final String name : context.getBeanDefinitionNames()) {
        log.trace("#main bean : {}={}", name, context.getBean(name));
      }
    }
  }
}
