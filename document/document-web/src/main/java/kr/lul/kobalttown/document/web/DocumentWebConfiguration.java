package kr.lul.kobalttown.document.web;

import kr.lul.kobalttown.document.borderline.DocumentBorderlineAnchor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@Configuration
@ComponentScan(basePackageClasses = {DocumentBorderlineAnchor.class})
public class DocumentWebConfiguration {
}
