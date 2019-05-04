package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.support.spring.boot.runner.DaemonServiceRunner;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@SpringBootApplication(scanBasePackageClasses = StandaloneRunnerConfiguration.class)
public class StandaloneRunner {
  private static final Logger log = getLogger(StandaloneRunner.class);

  public static void main(String... args) {
    DaemonServiceRunner.run(StandaloneRunner.class, args);
  }
}