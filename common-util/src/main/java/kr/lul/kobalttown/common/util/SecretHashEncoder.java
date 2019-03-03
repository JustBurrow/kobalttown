package kr.lul.kobalttown.common.util;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface SecretHashEncoder {
  String encode(CharSequence plain);

  boolean matches(CharSequence plain, String hash);
}