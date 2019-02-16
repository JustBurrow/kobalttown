package kr.lul.kobalttown.runner;

import kr.lul.kobalttown.configuration.ConfigurationAnchor;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * 애플리케이션을 실행 및 실행에 필요한 설정 정보를 제공.
 *
 * @author justburrow
 * @since 2018-12-25
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationAnchor.class})
public class KobalttownRunner {
  private static final Logger log = getLogger(KobalttownRunner.class);

  public static void main(String... args) {
    ApplicationContext context = SpringApplication.run(KobalttownRunner.class, args);

    if (log.isTraceEnabled()) {
      for (String name : context.getBeanDefinitionNames()) {
        log.trace("{}={}", name, context.getBean(name));
      }
    }
  }
}