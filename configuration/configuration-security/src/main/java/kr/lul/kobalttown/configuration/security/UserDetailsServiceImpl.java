package kr.lul.kobalttown.configuration.security;

import kr.lul.common.data.Context;
import kr.lul.common.util.Texts;
import kr.lul.kobalttown.account.data.dao.CredentialDao;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;
import kr.lul.kobalttown.configuration.bean.context.ContextService;
import kr.lul.support.spring.security.userdetails.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/12/07
 */
class UserDetailsServiceImpl implements UserDetailsService {
  private static final Logger log = getLogger(UserDetailsServiceImpl.class);

  @Autowired
  private CredentialDao credentialDao;
  @Autowired
  private ContextService uuidContextService;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.credentialDao,
        UserDetailsServiceImpl.class.getCanonicalName() + ".credentialDao does not autowired.");
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    if (log.isTraceEnabled())
      log.trace("#loadUserByUsername args : username={}", username);

    if (null == username || username.isEmpty())
      throw new UsernameNotFoundException("username is null or empty.");

    Context context = this.uuidContextService.get();
    if (null == context && log.isInfoEnabled())
      log.info("#loadUserByUsername context={}", context);

    Credential credential = this.credentialDao.read(context, username);
    if (log.isDebugEnabled())
      log.debug("#loadUserByUsername credential={}", credential);

    if (null == credential)
      throw new UsernameNotFoundException(format("no account for %s.", Texts.singleQuote(username)));

    Account account = credential.getAccount();
    if (log.isDebugEnabled())
      log.debug("#loadUserByUsername account={}", account);

    User user = new User(account.getId(), account.getNickname(), credential.getSecretHash(), account.isEnabled(),
        true, true, true, List.of(new SimpleGrantedAuthority("ROLE_USER")));

    if (log.isTraceEnabled())
      log.trace("#loadUserByUsername (context={}) return : {}", context, user);
    return user;
  }
}
