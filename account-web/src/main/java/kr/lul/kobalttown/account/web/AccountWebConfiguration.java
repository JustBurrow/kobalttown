package kr.lul.kobalttown.account.web;

import kr.lul.kobalttown.account.borderline.AccountBorderlineConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountBorderlineConfiguration.class})
public class AccountWebConfiguration {
}