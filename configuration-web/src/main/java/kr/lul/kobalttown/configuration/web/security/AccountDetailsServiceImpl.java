package kr.lul.kobalttown.configuration.web.security;

import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Service
class AccountDetailsServiceImpl implements AccountDetailsService {
  private static final Logger log = getLogger(AccountDetailsServiceImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobattown.configuration.web.AccountDetailsService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (log.isTraceEnabled()) {
      log.trace("args : username={}", username);
    }

    return null;
  }
}