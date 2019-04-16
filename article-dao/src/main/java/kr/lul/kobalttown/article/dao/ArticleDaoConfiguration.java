package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.jpa.ArticleJpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@Configuration
@ComponentScan(basePackageClasses = {ArticleJpaConfiguration.class})
public class ArticleDaoConfiguration {
}