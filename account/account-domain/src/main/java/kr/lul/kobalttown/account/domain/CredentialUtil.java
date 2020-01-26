package kr.lul.kobalttown.account.domain;

import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.common.util.Arguments.notEmpty;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.kobalttown.account.domain.Credential.PUBLIC_KEY_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Credential.PUBLIC_KEY_MIN_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * {@link Credential}용 유틸리티.
 * 테스트용으로 사용하시오.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public abstract class CredentialUtil {
  private static final ThreadLocalRandom random = ThreadLocalRandom.current();

  /**
   * @return {@link Credential#getPublicKey()}용 이메일.
   */
  public static String email() {
    return email("example.com");
  }


  /**
   * @param domain 이메일의 도메인.
   *
   * @return {@link Credential#getPublicKey()}용 이메일.
   */
  public static String email(final String domain) {
    notEmpty(domain, "domain");
    return "publickey." + random.nextInt(1, Integer.MAX_VALUE) + "@" + domain;
  }

  public static int userKeyLength() {
    return random.nextInt(PUBLIC_KEY_MIN_LENGTH, PUBLIC_KEY_MAX_LENGTH + 1);
  }

  /**
   * @return {@link Credential#getPublicKey()}용 유저 키.
   */
  public static String userKey() {
    return userKey(userKeyLength());
  }

  public static String userKey(final int length) {
    positive(length, "length");

    return random(length);
  }

  protected CredentialUtil() {
    throw new UnsupportedOperationException();
  }
}
