package kr.lul.kobalttown.article.jpa;

import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@SpringBootApplication(scanBasePackageClasses = {ConfigurationJpaConfiguration.class, ArticleJpaConfiguration.class})
public class ArticleJpaTestConfiguration {
}