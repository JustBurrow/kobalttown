package kr.lul.kobalttown.article.jpa;

import kr.lul.kobalttown.account.jpa.AccountJpaConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-03-31
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountJpaConfiguration.class})
public class ArticleJpaConfiguration {
}