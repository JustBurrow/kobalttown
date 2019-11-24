package kr.lul.kobalttown.account.dao;

import kr.lul.common.util.SystemTimeProvider;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication
@Import({JpaConfiguration.class})
public class AccountDaoTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}
