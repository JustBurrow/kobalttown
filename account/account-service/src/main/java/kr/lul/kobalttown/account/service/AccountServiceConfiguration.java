package kr.lul.kobalttown.account.service;

import kr.lul.kobalttown.account.data.dao.AccountDaoAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@ComponentScan(basePackageClasses = AccountDaoAnchor.class)
public class AccountServiceConfiguration {
}
