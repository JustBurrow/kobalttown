package kr.lul.kobalttown.account.domain;

import static java.lang.String.format;
import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;

/**
 * {@link Account}용 유틸리티.
 * 테스트용으로 사용하시오.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public abstract class AccountUtil {
  /**
   * @return 임의의 닉네임.
   */
  public static String nickname() {
    return nickname(current().nextInt(1, NICKNAME_MAX_LENGTH + 1));
  }

  public static String nickname(final int length) {
    positive(length);
    if (NICKNAME_MAX_LENGTH < length)
      throw new IllegalArgumentException(format("too long length : length=%d, max=%d", length, NICKNAME_MAX_LENGTH));

    return random(length);
  }

  protected AccountUtil() {
    throw new UnsupportedOperationException();
  }
}
