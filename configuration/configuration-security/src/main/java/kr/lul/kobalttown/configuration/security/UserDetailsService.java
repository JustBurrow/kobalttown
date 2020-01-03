package kr.lul.kobalttown.configuration.security;

import kr.lul.support.spring.security.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {
  @Override
  User loadUserByUsername(String username) throws UsernameNotFoundException;
}
