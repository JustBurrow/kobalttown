package kr.lul.kobalttown.document.data;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.account.test.AccountTestAnchor;
import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2020/02/07
 */
@SpringBootApplication(scanBasePackageClasses = {DocumentDataAnchor.class, AccountDataAnchor.class, AccountTestAnchor.class})
@Import({BeanConfiguration.class, JpaConfiguration.class})
public class DocumentDataTestConfiguration {
}
