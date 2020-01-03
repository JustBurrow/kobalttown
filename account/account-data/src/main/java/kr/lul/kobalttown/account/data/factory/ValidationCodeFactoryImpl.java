package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public class ValidationCodeFactoryImpl implements ValidationCodeFactory {
  private static final Logger log = getLogger(ValidationCodeFactoryImpl.class);

  @Override
  public ValidationCode create(final Context context, final Account account, final String code, final Instant createdAt) {
    return null;
  }

  @Override
  public ValidationCode create(final Context context, final Account account, final String code, final Duration ttl, final Instant createdAt) {
    return null;
  }

  @Override
  public ValidationCode create(final Context context, final Account account, final String code, final Instant expireAt, final Instant createdAt) {
    return null;
  }
}
