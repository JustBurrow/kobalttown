package kr.lul.kobalttown.test.account.domain;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.kobalttown.account.domain.Credential.SECRET_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MIN_LENGTH;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public abstract class CredentialUtils {
  /**
   * @return 임의의 평문 비밀번호.
   */
  public static String password() {
    Random random = ThreadLocalRandom.current();
    return RandomStringUtils.random(SECRET_MIN_LENGTH + random.nextInt(1 + SECRET_MAX_LENGTH - SECRET_MIN_LENGTH));
  }

  public CredentialUtils() {
    throw new UnsupportedOperationException();
  }
}