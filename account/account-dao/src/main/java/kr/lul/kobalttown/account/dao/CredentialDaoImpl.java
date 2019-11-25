package kr.lul.kobalttown.account.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.data.entity.CredentialEntity;
import kr.lul.kobalttown.account.data.repository.CredentialRepository;
import kr.lul.kobalttown.account.domain.Credential;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static kr.lul.common.util.Arguments.notNull;
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
  public Credential create(Context<UUID> context, Credential credential) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, credential={}", context, credential);
    notNull(context, "context");
    notNull(credential, "credential");

    CredentialEntity saved = this.repository.save((CredentialEntity) credential);

    if (log.isTraceEnabled())
      log.trace("#create return : {}", saved);
    return saved;
  }
}
