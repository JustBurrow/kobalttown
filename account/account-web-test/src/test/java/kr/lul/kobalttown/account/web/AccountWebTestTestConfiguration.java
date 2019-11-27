package kr.lul.kobalttown.account.web;

import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.kobalttown.configuration.security.WebSecurityConfiguration;
import kr.lul.kobalttown.configuration.web.WebMvcConfiguration;
import org.hibernate.validator.internal.metadata.raw.BeanConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/27
 */
@SpringBootApplication(scanBasePackageClasses = AccountWebAnchor.class)
@Import({JpaConfiguration.class, WebMvcConfiguration.class, WebSecurityConfiguration.class, BeanConfiguration.class})
public class AccountWebTestTestConfiguration {
}
