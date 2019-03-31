package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.converter.AccountConverterConfiguration;
import kr.lul.kobalttown.account.service.AccountServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-02-27
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountConverterConfiguration.class, AccountServiceConfiguration.class})
public class AccountBorderlineConfiguration {
}