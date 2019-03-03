package kr.lul.kobalttown.support.spring.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public interface AccountDetailsService extends UserDetailsService {
  @Override
  AccountDetails loadUserByUsername(String publicKey) throws UsernameNotFoundException;
}