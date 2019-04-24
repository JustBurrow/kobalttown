package kr.lul.kobalttown.article.converter;

import kr.lul.kobalttown.account.converter.AccountConverterConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-24
 */
@Configuration
@ComponentScan(basePackageClasses = {AccountConverterConfiguration.class})
public class ArticleConverterConfiguration {
}