package kr.lul.kobalttown.test.account.domain;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Account.isValidateNickname;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Credential.SECRET_MIN_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * @author justburrow
 * @since 2019-04-01
 */
public class AccountDomainTestUtil {
  /**
   * @return 임의의 유효한 닉네임.
   */
  public static String nickname() {
    Random random = ThreadLocalRandom.current();

    String nickname;
    do {
      nickname = random(1 + random.nextInt(NICKNAME_MAX_LENGTH));
    } while (!isValidateNickname(nickname));

    return nickname;
  }

  /**
   * @return 임의의 평문 비밀번호.
   */
  public static String password() {
    Random random = ThreadLocalRandom.current();
    return RandomStringUtils.random(SECRET_MIN_LENGTH + random.nextInt(1 + SECRET_MAX_LENGTH - SECRET_MIN_LENGTH));
  }
}