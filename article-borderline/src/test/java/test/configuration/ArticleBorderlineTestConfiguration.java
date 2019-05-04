package test.configuration;

import kr.lul.kobalttown.article.borderline.ArticleBorderlineConfiguration;
import kr.lul.kobalttown.common.util.MillisSystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.security.ConfigurationSecurityConfiguration;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import kr.lul.kobalttown.test.article.ArticleBorderlineTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author justburrow
 * @since 2019-04-25
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleBorderlineConfiguration.class,
    ConfigurationSecurityConfiguration.class, ConfigurationJpaConfiguration.class})
public class ArticleBorderlineTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new MillisSystemTimeProvider();
  }

  @Bean
  public ArticleBorderlineTestUtil articleBorderlineTestUtil() {
    return new ArticleBorderlineTestUtil();
  }

  @Bean
  public AccountServiceTestUtil accountServiceTestUtil() {
    return new AccountServiceTestUtil();
  }
}