package kr.lul.kobalttown.support.spring.boot.runner;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * 물리 서버, 혹은 VM에서 실행할 때 사용하는 러너.
 * 시스템이 살아있는 동안 애플리케이션의 실행 - 종료 - 재실행을 반복할 때 사용한다.
 *
 * @author justburrow
 * @since 2019-03-14
 */
public class DaemonServiceRunner {
  private static final Logger log = getLogger(DaemonServiceRunner.class);

  /**
   * 추가 조작을 할 때.
   *
   * @param runnerClass
   * @param args
   *
   * @return
   */
  public static DaemonServiceRunner newInstance(Class runnerClass, String... args) {
    DaemonServiceRunner dsr = new DaemonServiceRunner(runnerClass, args);
    dsr.init();
    return dsr;
  }

  /**
   * 기본 설정으로 바로 실행할 때.
   *
   * @param runnerClass
   * @param args
   *
   * @return
   */
  public static DaemonServiceRunner run(Class runnerClass, String... args) {
    DaemonServiceRunner dsr = new DaemonServiceRunner(runnerClass, args);
    dsr.init();
    dsr.run();
    return dsr;
  }

  private final Class runnerClass;
  private final List<String> args;

  private SpringApplication application;
  private ConfigurableApplicationContext context;

  private DaemonServiceRunner(Class runnerClass, String... args) {
    notNull(runnerClass, "runnerClass");
    notNull(args, "args");

    this.runnerClass = runnerClass;
    this.args = List.of(args);
  }

  private void init() {
    this.application = new SpringApplication(this.runnerClass);
    this.application.addListeners(new ApplicationPidFileWriter());
  }

  public ConfigurableApplicationContext run() {
    this.context = this.application.run(this.args.toArray(new String[this.args.size()]));
    if (log.isTraceEnabled()) {
      for (String name : this.context.getBeanDefinitionNames()) {
        Object bean = this.context.getBean(name);
        log.trace("bean : {}={}", name, bean);
      }
    }
    return this.context;
  }
}