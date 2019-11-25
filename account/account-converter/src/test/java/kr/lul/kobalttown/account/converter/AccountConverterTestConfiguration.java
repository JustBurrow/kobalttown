package kr.lul.kobalttown.account.converter;

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
@SpringBootApplication(scanBasePackageClasses = AccountConverterAnchor.class)
@Import({JpaConfiguration.class})
public class AccountConverterTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}
