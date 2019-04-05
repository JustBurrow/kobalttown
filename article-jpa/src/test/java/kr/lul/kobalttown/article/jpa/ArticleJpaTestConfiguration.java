package kr.lul.kobalttown.article.jpa;

import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.jpa.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.jpa.ArticleJpaTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationJpaConfiguration.class, ArticleJpaConfiguration.class})
public class ArticleJpaTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccountJpaTestUtil accountJpaTestUtil() {
    return new AccountJpaTestUtil();
  }

  @Bean
  public ArticleJpaTestUtil articleJpaTestUtil() {
    return new ArticleJpaTestUtil();
  }
}