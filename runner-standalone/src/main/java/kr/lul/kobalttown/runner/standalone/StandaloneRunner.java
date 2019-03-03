package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.account.web.AccountWebConfiguration;
import kr.lul.kobalttown.configuration.bean.ConfigurationBeanConfiguration;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.web.ConfigurationWebConfiguration;
import kr.lul.kobalttown.root.web.RootWebConfiguration;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@SpringBootApplication(scanBasePackageClasses = {
    ConfigurationBeanConfiguration.class, ConfigurationWebConfiguration.class, ConfigurationJpaConfiguration.class,
    AccountWebConfiguration.class, RootWebConfiguration.class})
public class StandaloneRunner {
  private static final Logger log = getLogger(StandaloneRunner.class);

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(StandaloneRunner.class, args);
    if (log.isTraceEnabled()) {
      for (String name : context.getBeanDefinitionNames()) {
        Object bean = context.getBean(name);
        log.trace("bean : {}={}", name, bean);
      }
    }
  }
}