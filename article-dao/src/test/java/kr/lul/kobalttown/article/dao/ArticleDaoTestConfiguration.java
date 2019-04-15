package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.jpa.ArticleJpaConfiguration;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@SpringBootApplication(scanBasePackageClasses = {ArticleDaoConfiguration.class, ConfigurationJpaConfiguration.class})
@EntityScan(basePackageClasses = {ArticleJpaConfiguration.class})
@EnableJpaRepositories(basePackageClasses = {ArticleJpaConfiguration.class})
public class ArticleDaoTestConfiguration {
}