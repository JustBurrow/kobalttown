package kr.lul.kobalttown.support.spring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public class SecretHashEncoder {
  private PasswordEncoder encoder;

  public SecretHashEncoder() {
    this(new BCryptPasswordEncoder());
  }

  public SecretHashEncoder(PasswordEncoder encoder) {
    notNull(encoder, "encoder");

    this.encoder = encoder;
  }

  public String encode(CharSequence plain) {
    return this.encoder.encode(plain);
  }

  public boolean matches(CharSequence plain, String hash) {
    return this.encoder.matches(plain, hash);
  }
}