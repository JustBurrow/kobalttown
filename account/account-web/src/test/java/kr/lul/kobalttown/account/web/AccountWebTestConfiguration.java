package kr.lul.kobalttown.account.web;

import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = AccountWebAnchor.class)
@Import({JpaConfiguration.class, WebMvcConfiguration.class})
public class AccountWebTestConfiguration {
}
