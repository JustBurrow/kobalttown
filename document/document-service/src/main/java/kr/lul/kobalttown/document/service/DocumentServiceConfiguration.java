package kr.lul.kobalttown.document.service;

import kr.lul.kobalttown.document.data.DocumentDataAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@Configuration
@ComponentScan(basePackageClasses = {DocumentDataAnchor.class})
public class DocumentServiceConfiguration {
}
