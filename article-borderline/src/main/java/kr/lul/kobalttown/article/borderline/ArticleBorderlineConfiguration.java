package kr.lul.kobalttown.article.borderline;

import kr.lul.kobalttown.account.service.AccountServiceConfiguration;
import kr.lul.kobalttown.article.converter.ArticleConverterConfiguration;
import kr.lul.kobalttown.article.service.ArticleServiceConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-25
 */
@Configuration
@ComponentScan(basePackageClasses = {ArticleServiceConfiguration.class, ArticleConverterConfiguration.class,
    AccountServiceConfiguration.class})
public class ArticleBorderlineConfiguration {
}