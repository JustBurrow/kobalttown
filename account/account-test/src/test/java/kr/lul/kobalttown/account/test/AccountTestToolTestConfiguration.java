package kr.lul.kobalttown.account.test;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2020/02/08
 */
@SpringBootApplication(scanBasePackageClasses = {AccountTestAnchor.class, AccountDataAnchor.class})
@Import({BeanConfiguration.class, JpaConfiguration.class})
public class AccountTestToolTestConfiguration {
}
