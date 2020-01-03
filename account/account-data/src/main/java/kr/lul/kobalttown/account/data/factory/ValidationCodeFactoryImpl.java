package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.ValidationCodeEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.account.domain.ValidationCode.ATTR_ID;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/03
 */
@Service
public class ValidationCodeFactoryImpl implements ValidationCodeFactory {
  private static final Logger log = getLogger(ValidationCodeFactoryImpl.class);

  @Override
  public ValidationCode create(final Context context, final Account account, final String code,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, code={}, createdAt={}", context, account, code, createdAt);
    notNull(context, "context");

    final ValidationCodeEntity validationCode = new ValidationCodeEntity(account, code, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, validationCode);
    return validationCode;
  }

  @Override
  public ValidationCode create(final Context context, final Account account, final String code, final Duration ttl,
      final Instant createdAt) {
    notNull(context, "context");
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, code={}, ttl={}, createdAt={}",
          context, account, code, ttl, createdAt);

    final ValidationCodeEntity validationCode = new ValidationCodeEntity(account, code, ttl, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, validationCode);
    return validationCode;
  }

  @Override
  public ValidationCode create(final Context context, final Account account, final String code, final Instant expireAt,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace(
          "#create args : context={}, account={}, code={}, expireAt={}, createdAt={}",
          context, account, code, expireAt, createdAt);
    notNull(context, "context");

    final ValidationCode validationCode = new ValidationCodeEntity(account, code, expireAt, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, validationCode);
    return validationCode;
  }

  @Override
  public ValidationCode create(final long id, final Account account, final String code, final Instant expireAt,
      final Instant usedAt, final Instant expiredAt, final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : id={}, account={}, code={}, expireAt={}, usedAt={}, expiredAt={}, createdAt={}",
          id, account, code, expireAt, usedAt, expiredAt, createdAt);
    if (null != usedAt && null != expiredAt)
      throw new IllegalArgumentException("both usedAt and expiredAt are set.");

    final ValidationCodeEntity validationCode = new ValidationCodeEntity(account, code, expireAt, createdAt);

    final Field field;
    try {
      field = ValidationCodeEntity.class.getDeclaredField(ATTR_ID);
      field.setAccessible(true);
      field.set(validationCode, id);
      field.setAccessible(false);
    } catch (final NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("fail to set id : id=" + id, e);
    }

    if (null != usedAt)
      validationCode.use(usedAt);
    else if (null != expiredAt)
      validationCode.expire(expiredAt);

    if (log.isTraceEnabled())
      log.trace("#create return : {}", validationCode);
    return validationCode;
  }
}
