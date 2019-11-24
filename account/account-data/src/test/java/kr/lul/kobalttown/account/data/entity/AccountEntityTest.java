package kr.lul.kobalttown.account.data.entity;

import kr.lul.common.util.ValidationException;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;
import org.junit.Test;
import org.slf4j.Logger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.account.domain.Account.ATTR_NICKNAME;
import static kr.lul.kobalttown.account.domain.Account.NICKNAME_MAX_LENGTH;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public class AccountEntityTest {
  private static final Logger log = getLogger(AccountEntityTest.class);

  @Test
  public void test_new_with_null_nickname() throws Exception {
    // WHEN & THEN
    assertThatThrownBy(() -> new AccountEntity(null))
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining("nickname is null.")
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, null);
  }

  @Test
  public void test_new_with_empty_nickname() throws Exception {
    assertThatThrownBy(() -> new AccountEntity(""))
        .isInstanceOf(ValidationException.class)
        .extracting("targetName", "target")
        .containsSequence(ATTR_NICKNAME, "");
  }

  @Test
  public void test_new_with_nickname() throws Exception {
    // GIVEN
    String nickname = random(current().nextInt(1, NICKNAME_MAX_LENGTH + 1));
    log.info("GIVEN - nickname={}", nickname);

    // WHEN
    AccountEntity account = new AccountEntity(nickname);
    log.info("WHEN - account={}", account);

    // THEN
    assertThat(account)
        .extracting(AccountEntity::getId, AccountEntity::getNickname, AccountEntity::isEnabled,
            SavableEntity::getCreatedAt, SavableEntity::getUpdatedAt)
        .containsSequence(0L, nickname, false,
            null, null);
  }
}
