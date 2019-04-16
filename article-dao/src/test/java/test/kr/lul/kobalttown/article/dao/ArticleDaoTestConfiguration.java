package test.kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.dao.ArticleDaoConfiguration;
import kr.lul.kobalttown.article.jpa.ArticleJpaConfiguration;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleDaoConfiguration.class, ArticleJpaConfiguration.class})
@EnableJpaRepositories(basePackages = ConfigurationJpaConfiguration.JPA_BASE_SCAN_PACKAGE)
@EntityScan(basePackages = ConfigurationJpaConfiguration.JPA_BASE_SCAN_PACKAGE)
public class ArticleDaoTestConfiguration {
}