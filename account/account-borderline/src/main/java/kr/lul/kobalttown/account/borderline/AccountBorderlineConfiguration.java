package kr.lul.kobalttown.account.borderline;

import kr.lul.kobalttown.account.converter.AccountConverterAnchor;
import kr.lul.kobalttown.account.service.AccountServiceAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountServiceAnchor.class, AccountConverterAnchor.class})
public class AccountBorderlineConfiguration {
}
