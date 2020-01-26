package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.ContinuousRange;
import kr.lul.common.util.Range;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.domain.EnableCodeStatusException;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static kr.lul.kobalttown.account.domain.EnableCode.Status.ISSUED;
import static kr.lul.kobalttown.account.domain.EnableCode.TTL_DEFAULT;
import static kr.lul.kobalttown.account.domain.EnableCodeUtil.token;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/26
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountConverterTestConfiguration.class)
public class EnableCodeConverterImplTest {
  protected static final Logger log = getLogger(EnableCodeConverterImplTest.class);

  @Autowired
  private EnableCodeConverterImpl converter;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() {
    this.before = Instant.now();
    log.info("SETUP - before={}", this.before);
  }

  @Test
  public void test_summary_with_null() throws Exception {
    assertThat(this.converter.summary(null))
        .isNull();
  }

  @Test
  public void test_convert_with_null_code() throws Exception {
    assertThat(this.converter.convert(null, EnableCodeSummaryDto.class))
        .isNull();
  }

  @Test
  public void test_convert_with_null_targetType() throws Exception {
    // GIVEN
    final String email = "enable-code-converter@lul.kr";
    final String token = token();
    log.info("GIVEN - email={}, token={}", email, token);
    final EnableCode code = new EnableCode() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public Account getAccount() {
        return new Account() {
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
            return EnableCodeConverterImplTest.this.before;
          }

          @Override
          public Instant getUpdatedAt() {
            return EnableCodeConverterImplTest.this.before;
          }
        };
      }

      @Override
      public String getEmail() {
        return email;
      }

      @Override
      public String getToken() {
        return token;
      }

      @Override
      public Instant getExpireAt() {
        return EnableCodeConverterImplTest.this.before.plus(TTL_DEFAULT);
      }

      @Override
      public Status getStatus() {
        return ISSUED;
      }

      @Override
      public Instant getStatusAt() {
        return EnableCodeConverterImplTest.this.before;
      }

      @Override
      public boolean isUsed() {
        return false;
      }

      @Override
      public boolean isExpired() {
        return false;
      }

      @Override
      public Range<Instant> getValidRange() {
        return new ContinuousRange<>(EnableCodeConverterImplTest.this.before.plus(USE_INTERVAL_MIN), true, getExpireAt(), true);
      }

      @Override
      public boolean isValid(final Instant statusAt) {
        return true;
      }

      @Override
      public void use(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public void expire(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public void inactive(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public Instant getCreatedAt() {
        return EnableCodeConverterImplTest.this.before;
      }

      @Override
      public Instant getUpdatedAt() {
        return EnableCodeConverterImplTest.this.before;
      }
    };
    log.info("GIVEN - code={}", code);

    // WHEN
    final IllegalArgumentException ex = catchThrowableOfType(() -> this.converter.convert(code, null),
        IllegalArgumentException.class);
    log.info("WHEN - ex=" + ex, ex);

    // THEN
    assertThat(ex)
        .isNotNull()
        .hasMessage("targetType is null.");
  }

  @Test
  public void test_convert_to_summary() throws Exception {
    // GIVEN
    final String email = "enable-code-converter@lul.kr";
    final String token = token();
    final Instant expireAt = this.before.plus(TTL_DEFAULT);
    log.info("GIVEN - email={}, token={}", email, token);
    final EnableCode code = new EnableCode() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public Account getAccount() {
        return new Account() {
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
            return EnableCodeConverterImplTest.this.before;
          }

          @Override
          public Instant getUpdatedAt() {
            return EnableCodeConverterImplTest.this.before;
          }
        };
      }

      @Override
      public String getEmail() {
        return email;
      }

      @Override
      public String getToken() {
        return token;
      }

      @Override
      public Instant getExpireAt() {
        return EnableCodeConverterImplTest.this.before.plus(TTL_DEFAULT);
      }

      @Override
      public Status getStatus() {
        return ISSUED;
      }

      @Override
      public Instant getStatusAt() {
        return EnableCodeConverterImplTest.this.before;
      }

      @Override
      public boolean isUsed() {
        return false;
      }

      @Override
      public boolean isExpired() {
        return false;
      }

      @Override
      public Range<Instant> getValidRange() {
        return new ContinuousRange<>(EnableCodeConverterImplTest.this.before.plus(USE_INTERVAL_MIN), true, getExpireAt(), true);
      }

      @Override
      public boolean isValid(final Instant statusAt) {
        return true;
      }

      @Override
      public void use(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public void expire(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public void inactive(final Instant now) throws EnableCodeStatusException {

      }

      @Override
      public Instant getCreatedAt() {
        return EnableCodeConverterImplTest.this.before;
      }

      @Override
      public Instant getUpdatedAt() {
        return EnableCodeConverterImplTest.this.before;
      }
    };
    log.info("GIVEN - code={}", code);

    // WHEN
    final EnableCodeSummaryDto dto = this.converter.convert(code, EnableCodeSummaryDto.class);
    log.info("WHEN - dto={}", dto);

    // THEN
    assertThat(dto)
        .isNotNull()
        .extracting(EnableCodeSummaryDto::getId, EnableCodeSummaryDto::getEmail,
            EnableCodeSummaryDto::getToken, EnableCodeSummaryDto::getExpireAt)
        .containsSequence(1L, email,
            token, this.timeProvider.zonedDateTime(expireAt));
  }
}
