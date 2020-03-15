package kr.lul.kobalttown.document.service;

import kr.lul.kobalttown.document.test.DocumentTestAnchor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@SpringBootApplication(scanBasePackageClasses = {DocumentServiceAnchor.class, DocumentTestAnchor.class})
public class DocumentServiceTestConfiguration {
}
