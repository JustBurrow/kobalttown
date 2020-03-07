package kr.lul.kobalttown.document.borderline;

import kr.lul.kobalttown.account.service.AccountServiceAnchor;
import kr.lul.kobalttown.document.converter.DocumentConverterAnchor;
import kr.lul.kobalttown.document.service.DocumentServiceAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@Configuration
@ComponentScan(basePackageClasses = {DocumentServiceAnchor.class, DocumentConverterAnchor.class, AccountServiceAnchor.class})
public class DocumentBorderlineConfiguration {
}
