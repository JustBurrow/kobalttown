package kr.lul.kobalttown.article.service;

import kr.lul.kobalttown.article.dao.ArticleDaoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@Configuration
@ComponentScan(basePackageClasses = {ArticleDaoConfiguration.class})
public class ArticleServiceConfiguration {
}