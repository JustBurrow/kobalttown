package test.configuration;

import kr.lul.kobalttown.account.service.AccountServiceConfiguration;
import kr.lul.kobalttown.article.converter.ArticleConverterConfiguration;
import kr.lul.kobalttown.article.service.ArticleServiceConfiguration;
import kr.lul.kobalttown.common.util.MillisSystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.support.spring.security.SecretHashEncoder;
import kr.lul.kobalttown.test.account.AccountServiceTestUtil;
import kr.lul.kobalttown.test.article.ArticleServiceTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-24
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleConverterConfiguration.class,
    ArticleServiceConfiguration.class, AccountServiceConfiguration.class,
    ConfigurationJpaConfiguration.class})
public class ArticleConverterTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new MillisSystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecretHashEncoder secretHashEncoder() {
    return new SecretHashEncoder(passwordEncoder());
  }

  @Bean
  public ArticleServiceTestUtil articleServiceTestUtil() {
    return new ArticleServiceTestUtil();
  }

  @Bean
  public AccountServiceTestUtil accountServiceTestUtil() {
    return new AccountServiceTestUtil();
  }
}