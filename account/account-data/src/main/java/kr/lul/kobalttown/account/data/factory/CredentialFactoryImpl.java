package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.Instant;

import static kr.lul.common.util.Arguments.notNegative;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.account.domain.Credential.ATTR_ID;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/07
 */
@Service
class CredentialFactoryImpl implements CredentialFactory {
  private static final Logger log = getLogger(CredentialFactoryImpl.class);

  @Override
  public Credential create(final Context context, final Account account, final String publicKey,
      final String secretHash,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, publicKey={}, secretHash={}, createdAt={}",
          context, account, publicKey, secretHash, createdAt);
    notNull(context, "context");

    final CredentialEntity credential = new CredentialEntity(account, publicKey, secretHash, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, credential);
    return credential;
  }

  @Override
  public Credential create(
      final long id, final Account account, final String publicKey, final String secretHash, final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : id={}, account={}, publicKey={}, secretHash={}, createdAt={}",
          id, account, publicKey, secretHash, createdAt);
    notNegative(id, "id");

    final CredentialEntity credential = new CredentialEntity(account, publicKey, secretHash, createdAt);

    if (0L < id) {
      try {
        final Field field = CredentialEntity.class.getDeclaredField(ATTR_ID);
        field.setAccessible(true);
        field.set(credential, id);
        field.setAccessible(false);
      } catch (final NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException("fail to set id : id=" + id, e);
      }
    }

    if (log.isTraceEnabled())
      log.trace("#create return : {}", credential);
    return credential;
  }
}
