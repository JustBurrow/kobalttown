package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AbstractAccountDto;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountConverterTestConfiguration.class)
@Transactional
public class AccountConverterImplTest {
  private static final Logger log = getLogger(AccountConverterImplTest.class);

  @Autowired
  private AccountConverter converter;
  @Autowired
  private TimeProvider timeProvider;

  private ZonedDateTime zonedDateTime;

  @Before
  public void setUp() throws Exception {
    assertThat(this.converter).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.zonedDateTime = this.timeProvider.zonedDateTime();
    log.info("SETUP - zonedDateTime={}", this.zonedDateTime);
  }

  @Test
  public void test_supportTargetTypes() throws Exception {
    assertThat(this.converter.supportTargetTypes())
        .containsOnly(AccountDetailDto.class);
  }

  @Test
  public void test_convert_with_null_account_and_null_target_type() throws Exception {
    assertThatThrownBy(() -> this.converter.convert(null, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("targetType is null.");
  }

  @Test
  public void test_convert_with_null_account_and_detail_target_type() throws Exception {
    assertThat(this.converter.convert(null, AccountDetailDto.class))
        .isNull();
  }

  @Test
  public void test_convert_to_detail() throws Exception {
    // GIVEN
    final Account account = new AccountEntity("nickname", false, this.zonedDateTime.toInstant());
    log.info("GIVEN - account={}", account);

    // WHEN
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .extracting(AbstractAccountDto::getId, AbstractAccountDto::getNickname,
            AccountDetailDto::getCreatedAt, AccountDetailDto::getUpdatedAt)
        .containsSequence(account.getId(), "nickname", this.zonedDateTime, this.zonedDateTime);
  }
}
