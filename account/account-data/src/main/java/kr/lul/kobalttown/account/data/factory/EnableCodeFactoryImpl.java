package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.EnableCodeEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.domain.EnableCode.Status;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.account.domain.EnableCode.ATTR_ID;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Service
public class EnableCodeFactoryImpl implements EnableCodeFactory {
  private static final Logger log = getLogger(EnableCodeFactoryImpl.class);

  @Override
  public EnableCode create(final Context context, final Account account, final String email, final String token,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, email={}, token={}, createdAt={}",
          context, account, email, token, createdAt);
    notNull(context, "context");

    final EnableCodeEntity code = new EnableCodeEntity(account, email, token, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, code);
    return code;
  }

  @Override
  public EnableCode create(final Context context, final Account account, final String email, final String token,
      final Duration ttl, final Instant createdAt) {
    notNull(context, "context");
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, email={} token={}, ttl={}, createdAt={}",
          context, account, email, token, ttl, createdAt);

    final EnableCodeEntity code = new EnableCodeEntity(account, email, token, ttl, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, code);
    return code;
  }

  @Override
  public EnableCode create(final Context context, final Account account, final String email, final String token,
      final Instant expireAt, final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, email={}, token={}, expireAt={}, createdAt={}",
          context, account, email, token, expireAt, createdAt);
    notNull(context, "context");

    final EnableCode code = new EnableCodeEntity(account, email, token, expireAt, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, code);
    return code;
  }

  @Override
  public EnableCode create(final long id, final Account account, final String email, final String token,
      final Instant expireAt, final Status status, final Instant statusAt, final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : id={}, account={}, email={}, token={}, expireAt={}, status={}, statusAt={}, createdAt={}",
          id, account, email, token, expireAt, status, statusAt, createdAt);
    if (null != status)
      notNull(statusAt, "statusAt");

    final EnableCodeEntity code = new EnableCodeEntity(account, email, token, expireAt, createdAt);

    final Field field;
    try {
      field = EnableCodeEntity.class.getDeclaredField(ATTR_ID);
      field.setAccessible(true);
      field.set(code, id);
      field.setAccessible(false);
    } catch (final NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("fail to set id : id=" + id, e);
    }

    if (null != status) {
      notNull(statusAt, "statusAt");

      switch (status) {
        case USED: {
          code.use(statusAt);
          break;
        }
        case EXPIRED: {
          code.expire(statusAt);
          break;
        }
      }
    }

    if (log.isTraceEnabled())
      log.trace("#create return : {}", code);
    return code;
  }
}
