package test.configuration;

import kr.lul.kobalttown.account.dao.AccountDaoConfiguration;
import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.article.dao.ArticleDaoConfiguration;
import kr.lul.kobalttown.article.jpa.ArticleJpaConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.test.account.AccountDaoTestUtil;
import kr.lul.kobalttown.test.account.AccountDomainTestUtil;
import kr.lul.kobalttown.test.account.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.ArticleDaoTestUtil;
import kr.lul.kobalttown.test.article.ArticleDomainTestUtil;
import kr.lul.kobalttown.test.article.ArticleJpaTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-04-17
 */
@SpringBootApplication(scanBasePackageClasses = {AccountJpaConfiguration.class, AccountDaoConfiguration.class,
    ArticleJpaConfiguration.class, ArticleDaoConfiguration.class,
    ConfigurationJpaConfiguration.class})
public class TestUtilTestConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AccountDomainTestUtil accountDomainTestUtil() {
    return new AccountDomainTestUtil();
  }

  @Bean
  public AccountJpaTestUtil accountJpaTestUtil() {
    return new AccountJpaTestUtil();
  }

  @Bean
  public AccountDaoTestUtil accountDaoTestUtil() {
    return new AccountDaoTestUtil();
  }

  @Bean
  public ArticleDomainTestUtil articleDomainTestUtil() {
    return new ArticleDomainTestUtil();
  }

  @Bean
  public ArticleJpaTestUtil articleJpaTestUtil() {
    return new ArticleJpaTestUtil();
  }

  @Bean
  public ArticleDaoTestUtil articleDaoTestUtil() {
    return new ArticleDaoTestUtil();
  }
}