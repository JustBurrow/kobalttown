package kr.lul.kobalttown.configuration;

import kr.lul.kobalttown.web.context.BasicPapermarkConverter;
import kr.lul.kobalttown.web.context.PapermarkConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link kr.lul.kobalttown.web} 패키지용 기타 빈 설정.
 *
 * @author justburrow
 * @since 2019-01-05
 */
@Configuration
public class WebBeanConfiguration {
  @Bean
  public PapermarkConverter papermarkConverter() {
    return new BasicPapermarkConverter();
  }
}