package kr.lul.kobalttown.account.dao;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@ComponentScan(basePackageClasses = AccountDataAnchor.class)
public class AccountDaoConfiguration {
}
