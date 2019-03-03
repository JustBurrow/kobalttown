package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.dao.AccountDaoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountDaoConfiguration.class})
public class AccountServiceConfiguration {
}