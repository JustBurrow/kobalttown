package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/07
 */
@Service
class CredentialFactoryImpl implements CredentialFactory {
  private static final Logger log = getLogger(CredentialFactoryImpl.class);

  @Override
  public Credential create(Context context, Account account, String publicKey, String secretHash,
      Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, account={}, publicKey={}, secretHash={}, createdAt={}",
          context, account, publicKey, secretHash, createdAt);

    CredentialEntity credential = new CredentialEntity(account, publicKey, secretHash, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, credential);
    return credential;
  }
}
