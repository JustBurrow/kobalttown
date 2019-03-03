package kr.lul.kobalttown.support.spring.security;

import kr.lul.kobalttown.common.util.SecretHashEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static kr.lul.kobalttown.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class BCryptSecretHashEncoder implements SecretHashEncoder {
  private BCryptPasswordEncoder encoder;

  public BCryptSecretHashEncoder() {
    this(new BCryptPasswordEncoder());
  }

  public BCryptSecretHashEncoder(BCryptPasswordEncoder encoder) {
    notNull(encoder, "encoder");

    this.encoder = encoder;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.util.SecretEncoder
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String encode(CharSequence plain) {
    return this.encoder.encode(plain);
  }

  @Override
  public boolean matches(CharSequence plain, String hash) {
    return this.encoder.matches(plain, hash);
  }
}