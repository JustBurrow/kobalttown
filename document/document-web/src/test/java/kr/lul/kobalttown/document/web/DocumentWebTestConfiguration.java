package kr.lul.kobalttown.document.web;

import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.mail.MailConfigurer;
import kr.lul.kobalttown.document.test.DocumentTestAnchor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@SpringBootApplication(scanBasePackageClasses = {DocumentWebAnchor.class, DocumentTestAnchor.class})
@Import({BeanConfiguration.class, MailConfigurer.class})
public class DocumentWebTestConfiguration {
}
