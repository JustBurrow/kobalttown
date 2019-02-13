package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.configuration.ConfigurationAnchor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2019-02-10
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationAnchor.class})
public class PaperLoaderTestConfiguration {
}