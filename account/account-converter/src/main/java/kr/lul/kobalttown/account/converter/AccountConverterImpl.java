package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountConverterImpl implements AccountConverter {
  private static final Logger log = getLogger(AccountConverterImpl.class);

  @Autowired
  private TimeProvider timeProvider;

  public AccountDetailDto detail(Account account) {
    return null == account
        ? null
        : new AccountDetailDto(account.getId(), account.getNickname(), account.isEnabled(),
        this.timeProvider.zonedDateTime(account.getCreatedAt()),
        this.timeProvider.zonedDateTime(account.getUpdatedAt()));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T convert(Account account, Class<T> targetType) {
    if (log.isTraceEnabled())
      log.trace("#convert args : account={}, targetType={}", account, targetType);
    notNull(targetType, "targetType");

    final T dto;
    if (null == account) {
      dto = null;
    } else if (AccountDetailDto.class == targetType) {
      dto = (T) detail(account);
    } else {
      throw new IllegalArgumentException(format("unsupported targetType : %s", targetType));
    }

    if (log.isTraceEnabled())
      log.trace("#convert return : {}", dto);
    return dto;
  }
}
