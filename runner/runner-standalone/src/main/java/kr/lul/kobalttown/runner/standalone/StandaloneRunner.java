package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.account.web.AccountWebAnchor;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
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
@SpringBootApplication(scanBasePackageClasses = {AccountWebAnchor.class})
@Import({JpaConfiguration.class, WebMvcConfiguration.class})
public class StandaloneRunner {
  private static final Logger log = getLogger(StandaloneRunner.class);

  public static void main(String... args) {
    SpringApplication application = new SpringApplication(StandaloneRunner.class);
    application.addListeners(new ApplicationPidFileWriter());
    ApplicationContext context = application.run(args);

    if (log.isTraceEnabled()) {
      for (String name : context.getBeanDefinitionNames()) {
        log.trace("bean : %s=%s", name, context.getBean(name));
      }
    }
  }
}
