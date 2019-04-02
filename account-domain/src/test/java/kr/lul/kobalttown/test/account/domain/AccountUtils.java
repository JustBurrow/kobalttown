package kr.lul.kobalttown.test.account.domain;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static kr.lul.kobalttown.account.domain.Account.isValidateNickname;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * @author justburrow
 * @since 2019-04-01
 */
public abstract class AccountUtils {
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

  private AccountUtils() {
    throw new UnsupportedOperationException();
  }
}