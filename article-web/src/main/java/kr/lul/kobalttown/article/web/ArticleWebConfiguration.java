package kr.lul.kobalttown.article.web;

import kr.lul.kobalttown.article.borderline.ArticleBorderline;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-05-03
 */
@Configuration
@ComponentScan(basePackageClasses = {ArticleBorderline.class})
public class ArticleWebConfiguration {
}