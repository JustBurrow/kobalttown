package kr.lul.kobalttown.document.test;

import kr.lul.kobalttown.account.test.AccountTestAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountTestAnchor.class})
public class DocumentTestConfiguration {
}
