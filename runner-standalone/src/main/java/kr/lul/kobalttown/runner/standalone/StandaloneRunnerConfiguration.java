package kr.lul.kobalttown.runner.standalone;

import kr.lul.kobalttown.account.web.AccountWebConfiguration;
import kr.lul.kobalttown.article.web.ArticleWebConfiguration;
import kr.lul.kobalttown.common.util.SystemTimeProvider;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.configuration.jpa.ConfigurationJpaConfiguration;
import kr.lul.kobalttown.configuration.web.ConfigurationWebConfiguration;
import kr.lul.kobalttown.root.web.RootWebConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author justburrow
 * @since 2019-04-19
 */
@Configuration
@ComponentScan(basePackageClasses = {StandaloneRunnerConfiguration.class,
    RootWebConfiguration.class,
    AccountWebConfiguration.class, ArticleWebConfiguration.class,
    ConfigurationWebConfiguration.class, ConfigurationJpaConfiguration.class})
public class StandaloneRunnerConfiguration {
  @Bean
  public TimeProvider timeProvider() {
    return new SystemTimeProvider();
  }
}