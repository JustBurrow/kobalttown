package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import kr.lul.kobalttown.test.account.dao.AccountDaoTestUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author justburrow
 * @since 2019-04-10
 */
@SpringBootApplication(scanBasePackageClasses = {AccountDaoConfiguration.class,
    AccountJpaConfiguration.class})
@EntityScan(basePackageClasses = AccountJpaConfiguration.class)
@EnableJpaRepositories(basePackageClasses = AccountJpaConfiguration.class)
public class AccountDaoTestConfiguration {
  @Bean
  public AccountDaoTestUtil accountDaoTestUtil() {
    return new AccountDaoTestUtil();
  }
}