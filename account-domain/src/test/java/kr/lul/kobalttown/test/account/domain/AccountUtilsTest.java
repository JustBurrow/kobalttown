package kr.lul.kobalttown.test.account.domain;

import org.junit.Test;
import org.slf4j.Logger;

import static kr.lul.kobalttown.account.domain.Account.validateNickname;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-02
 */
public class AccountUtilsTest {
  private static final Logger log = getLogger(AccountUtilsTest.class);

  @Test
  public void test_nickname() throws Exception {
    for (int i = 0; i < 10000; i++) {
      // When
      String nickname = AccountUtils.nickname();

      // Then
      try {
        validateNickname(nickname);
      } catch (Exception e) {
        log.info("FAIL - nickname={}", nickname);
      }
    }
  }
}