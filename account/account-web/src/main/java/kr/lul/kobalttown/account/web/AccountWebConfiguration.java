package kr.lul.kobalttown.account.web;

import kr.lul.kobalttown.account.borderline.AccountBorderlineAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@ComponentScan(basePackageClasses = AccountBorderlineAnchor.class)
public class AccountWebConfiguration {
}
