package test.configuration;

import kr.lul.kobalttown.article.dao.ArticleDaoConfiguration;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleDaoConfiguration.class,
    ConfigurationJpaConfiguration.class})
public class ArticleDaoTestConfiguration {
}