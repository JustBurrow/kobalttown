package kr.lul.kobalttown.configuration.web.security;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.jpa.entity.CredentialEntity;
import kr.lul.kobalttown.account.jpa.repository.CredentialRepository;
import kr.lul.kobalttown.support.spring.security.AccountDetails;
import kr.lul.kobalttown.support.spring.security.AccountDetailsService;
import org.slf4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static kr.lul.kobalttown.common.util.Texts.singleQuote;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * TODO 모듈 및 패키지 변경.
 *
 * @author justburrow
 * @since 2019-03-03
 */
public class AccountDetailsServiceImpl implements AccountDetailsService {
  private static final Logger log = getLogger(AccountDetailsServiceImpl.class);

  private CredentialRepository credentialRepository;

  public AccountDetailsServiceImpl(CredentialRepository repository) {
    notNull(repository, "repository");

    this.credentialRepository = repository;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobattown.configuration.web.AccountDetailsService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public AccountDetails loadUserByUsername(String publicKey) throws UsernameNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : publicKey={}", publicKey);
    }

    CredentialEntity credential = this.credentialRepository.findByPublicKey(publicKey);
    if (null == credential) {
      throw new UsernameNotFoundException(format("no account for %s", singleQuote(publicKey)));
    }

    Account account = credential.getAccount();
    AccountDetails details = new AccountDetails(account.getId(), account.getNickname(), credential.getSecretHash(),
        new SimpleGrantedAuthority("ROLE_USER"));

    if (log.isTraceEnabled()) {
      log.trace("return : {}", details);
    }
    return details;
  }
}