package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Configuration
@ComponentScan(basePackageClasses = AccountJpaConfiguration.class)
public class AccountDaoConfiguration {
}