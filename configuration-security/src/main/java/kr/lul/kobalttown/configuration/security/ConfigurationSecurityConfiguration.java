package kr.lul.kobalttown.configuration.security;

import kr.lul.kobalttown.common.util.SecretHashEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Configuration
public class ConfigurationSecurityConfiguration {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecretHashEncoder secretHashEncoder() {
    return new SecretHashEncoder() {
      private PasswordEncoder passwordEncoder = passwordEncoder();

      @Override
      public String encode(CharSequence plain) {
        return this.passwordEncoder.encode(plain);
      }

      @Override
      public boolean matches(CharSequence plain, String hash) {
        return this.passwordEncoder.matches(plain, hash);
      }
    };
  }
}