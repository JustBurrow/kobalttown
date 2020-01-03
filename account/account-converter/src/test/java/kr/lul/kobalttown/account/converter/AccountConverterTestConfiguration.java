package kr.lul.kobalttown.account.converter;

import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = AccountConverterAnchor.class)
@Import({BeanConfiguration.class, JpaConfiguration.class})
public class AccountConverterTestConfiguration {
}
