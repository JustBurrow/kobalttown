package kr.lul.kobalttown.account.data.dao;

import kr.lul.kobalttown.account.data.AccountDataAnchor;
import kr.lul.kobalttown.configuration.bean.BeanConfiguration;
import kr.lul.kobalttown.configuration.data.jpa.JpaConfiguration;
import kr.lul.support.spring.security.crypto.PasswordEncoderSecurityEncoder;
import kr.lul.support.spring.security.crypto.SecurityEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@SpringBootApplication(scanBasePackageClasses = {AccountDataAnchor.class})
@Import({BeanConfiguration.class, JpaConfiguration.class})
public class AccountDaoTestConfiguration {
  @Bean
  public SecurityEncoder securityEncoder() {
    return new PasswordEncoderSecurityEncoder(new BCryptPasswordEncoder());
  }
}
