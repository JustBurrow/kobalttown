package test.configuration;

import kr.lul.kobalttown.article.dao.ArticleDaoConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.ArticleDaoTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleDaoConfiguration.class,
    ConfigurationJpaConfiguration.class})
public class ArticleDaoTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ArticleDaoTestUtil articleDaoTestUtil() {
    return new ArticleDaoTestUtil();
  }

  @Bean
  public AccountJpaTestUtil accountJpaTestUtil() {
    return new AccountJpaTestUtil();
  }
}