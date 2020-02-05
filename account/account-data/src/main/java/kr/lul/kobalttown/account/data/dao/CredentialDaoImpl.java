package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static kr.lul.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Service
class CredentialDaoImpl implements CredentialDao {
  private static final Logger log = getLogger(CredentialDaoImpl.class);

  @Autowired
  private CredentialRepository repository;

  @Override
  public Credential create(final Context context, final Credential credential) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, credential={}", context, credential);
    notNull(context, "context");
    notNull(credential, "credential");

    final CredentialEntity saved = this.repository.save((CredentialEntity) credential);

    if (log.isTraceEnabled())
      log.trace("#create ({}) return : {}", context, saved);
    return saved;
  }

  @Override
  public Credential read(final Context context, final String publicKey) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, publicKey={}", context, publicKey);
    notEmpty(publicKey, "publicKey");

    final CredentialEntity credential = this.repository.findByPublicKey(publicKey);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, credential);
    return credential;
  }

  @Override
  public List<Credential> read(final Context context, final Account account) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, account={}", context, account);
    notNull(account, "account");

    final List<Credential> credentials = new ArrayList<>(this.repository.findAllByAccount(account));

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, credentials);
    return credentials;
  }

  @Override
  public void delete(final Context context, final Credential credential) {
    if (log.isTraceEnabled())
      log.trace("#delete args : context={}, credential={}", context, credential);
    typeOf(credential, CredentialEntity.class, "credential");

    this.repository.delete((CredentialEntity) credential);
    this.repository.flush();

    if (log.isTraceEnabled())
      log.trace("#delete (context={}) result : void", context);
  }
}
