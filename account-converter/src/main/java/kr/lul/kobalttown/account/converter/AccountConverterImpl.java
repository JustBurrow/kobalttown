package kr.lul.kobalttown.account.converter;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;
import kr.lul.kobalttown.account.dto.SummaryAccountDto;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Service
class AccountConverterImpl implements AccountConverter {
  private static final Logger log = getLogger(AccountConverterImpl.class);

  private TimeProvider timeProvider;

  public AccountConverterImpl() {
  }

  public AccountConverterImpl(TimeProvider timeProvider) {
    notNull(timeProvider, "timeProvider");

    this.timeProvider = timeProvider;
  }

  public TimeProvider getTimeProvider() {
    return this.timeProvider;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.converter.AccountConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public <T> T convert(Account account, Class<T> targetType) {
    if (log.isTraceEnabled()) {
      log.trace("args : account={}, targetType={}", account, targetType);
    }
    notNull(targetType, "targetType");
    if (null == account) {
      return null;
    }

    T dto;
    if (SimpleAccountDto.class == targetType) {
      dto = (T) toSimpleAccountDto(account);
    } else if (SummaryAccountDto.class == targetType) {
      dto = (T) toSummaryAccountDto(account);
    } else {
      throw new IllegalArgumentException(format("unsupported target type : target=%s, supports=%s",
          targetType, SUPPORT_TYPES));
    }

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }

  @Override
  public SummaryAccountDto toSummaryAccountDto(Account account) {
    if (log.isTraceEnabled()) {
      log.trace("args : account={}", account);
    }

    SummaryAccountDto dto = new SummaryAccountDto(account.getId(), account.getNickname(),
        this.timeProvider.zonedDateTime(account.getCreatedAt()),
        this.timeProvider.zonedDateTime(account.getUpdatedAt()));

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }

  @Override
  public SimpleAccountDto toSimpleAccountDto(Account account) {
    if (log.isTraceEnabled()) {
      log.trace("args : account={}", account);
    }

    SimpleAccountDto dto = new SimpleAccountDto(account.getId(), account.getNickname());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }
}