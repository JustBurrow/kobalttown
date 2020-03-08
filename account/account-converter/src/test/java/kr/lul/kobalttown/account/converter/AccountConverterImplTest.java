package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AbstractAccountDto;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountConverterTestConfiguration.class)
public class AccountConverterImplTest {
  private static final Logger log = getLogger(AccountConverterImplTest.class);

  @Autowired
  private AccountConverterImpl converter;
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
        .containsOnly(AccountSimpleDto.class, AccountDetailDto.class);
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

  class UnsupportedDto {
  }

  @Test
  public void test_convert_with_unsupported_target_type() throws Exception {
    // GIVEN
    final Account account = new Account() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant enableAt) {

      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    };
    log.info("GIVEN - UnsupportedDto={}", UnsupportedDto.class);

    // WHEN
    final Throwable ex = catchThrowable(() -> this.converter.convert(account, UnsupportedDto.class));
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("unsupported targetType")
        .hasMessageContaining("targetType=" + UnsupportedDto.class.getCanonicalName());
  }

  @Test
  public void test_convert_to_detail() throws Exception {
    // GIVEN
    final Account account = new Account() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public String getNickname() {
        return "nickname";
      }

      @Override
      public boolean isEnabled() {
        return true;
      }

      @Override
      public void enable(final Instant enableAt) {
      }

      @Override
      public Instant getCreatedAt() {
        return AccountConverterImplTest.this.zonedDateTime.toInstant();
      }

      @Override
      public Instant getUpdatedAt() {
        return AccountConverterImplTest.this.zonedDateTime.toInstant();
      }
    };
    log.info("GIVEN - account={}", account);

    // WHEN
    final AccountDetailDto dto = this.converter.convert(account, AccountDetailDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .extracting(AbstractAccountDto::getId, AbstractAccountDto::getNickname, AccountDetailDto::isEnabled,
            AccountDetailDto::getCreatedAt, AccountDetailDto::getUpdatedAt)
        .containsSequence(1L, "nickname", true, this.zonedDateTime, this.zonedDateTime);
  }

  @Test
  public void test_detail_with_null() throws Exception {
    assertThat(this.converter.detail(null))
        .isNull();
  }

  @Test
  public void test_detail() throws Exception {
    // GIVEN
    final Account account = new Account() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public String getNickname() {
        return "nickname";
      }

      @Override
      public boolean isEnabled() {
        return true;
      }

      @Override
      public void enable(final Instant enableAt) {
      }

      @Override
      public Instant getCreatedAt() {
        return AccountConverterImplTest.this.zonedDateTime.toInstant();
      }

      @Override
      public Instant getUpdatedAt() {
        return AccountConverterImplTest.this.zonedDateTime.toInstant();
      }
    };
    log.info("GIVEN - account={}", account);

    // WHEN
    final AccountDetailDto dto = this.converter.detail(account);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(AccountDetailDto::getId, AbstractAccountDto::getNickname, AccountDetailDto::isEnabled,
            AccountDetailDto::getCreatedAt, AccountDetailDto::getUpdatedAt)
        .containsSequence(1L, "nickname", true, this.zonedDateTime, this.zonedDateTime);
  }
}
