package test.configuration;

import kr.lul.kobalttown.account.service.AccountServiceConfiguration;
import kr.lul.kobalttown.article.service.ArticleServiceConfiguration;
import kr.lul.kobalttown.common.util.MillisSystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.security.ConfigurationSecurityConfiguration;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import kr.lul.kobalttown.test.article.ArticleServiceTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleServiceConfiguration.class,
    AccountServiceConfiguration.class,
    ConfigurationJpaConfiguration.class, ConfigurationSecurityConfiguration.class})
public class ArticleServiceTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new MillisSystemTimeProvider();
  }

  @Bean
  public AccountServiceTestUtil accountServiceTestUtil() {
    return new AccountServiceTestUtil();
  }

  @Bean
  public ArticleServiceTestUtil articleServiceTestUtil() {
    return new ArticleServiceTestUtil();
  }
}