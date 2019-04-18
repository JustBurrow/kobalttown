package test.configuration;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.article.jpa.ArticleJpaConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.ArticleJpaTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleJpaConfiguration.class,
    ConfigurationJpaConfiguration.class})
@EntityScan(basePackageClasses = {ArticleJpaConfiguration.class, AccountJpaConfiguration.class})
@EnableJpaRepositories(basePackageClasses = {ArticleJpaConfiguration.class, AccountJpaConfiguration.class})
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