package kr.lul.kobalttown.document.converter;

import kr.lul.kobalttown.document.test.DocumentTestAnchor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@SpringBootApplication(scanBasePackageClasses = {NoteConverterAnchor.class, DocumentTestAnchor.class})
//@Import({BeanConfiguration.class})
public class NoteConverterTestConfiguration {
}
