package kr.lul.kobalttown.document.converter;

import kr.lul.kobalttown.account.converter.AccountConverterAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@Configuration
@ComponentScan(basePackageClasses = AccountConverterAnchor.class)
public class NoteConverterConfiguration {
}
