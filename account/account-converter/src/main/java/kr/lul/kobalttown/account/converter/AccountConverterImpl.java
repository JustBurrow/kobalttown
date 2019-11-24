package kr.lul.kobalttown.account.converter;

import kr.lul.kobalttown.account.domain.Account;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class AccountConverterImpl implements AccountConverter {
  private static final Logger log = getLogger(AccountConverterImpl.class);

  @Override
  public <T> T convert(Account source, Class<T> targetType) {
    return null;
  }
}
