package kr.lul.kobalttown.support.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * @author justburrow
 * @since 2019-03-03
 */
public class AccountDetails extends User {
  private int id;

  public AccountDetails(int id, String username, String password, GrantedAuthority... authorities) {
    this(id, username, password, asList(authorities));
  }

  public AccountDetails(int id, String username, String password,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.id = id;
  }

  public AccountDetails(int id, String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority... authorities) {
    this(id, username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
        asList(authorities));
  }

  public AccountDetails(int id, String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired, boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringBuilder(AccountDetails.class.getSimpleName())
        .append("{id=").append(this.id)
        .append(", username=").append(this.getUsername())
        .append(", password=[ PROTECTED ], enabled=").append(this.isEnabled())
        .append(", accountNonExpired=").append(this.isAccountNonExpired())
        .append(", credentialsNonExpired=").append(this.isCredentialsNonExpired())
        .append(", accountNonLocked=").append(this.isAccountNonLocked())
        .append(", grantedAuthorities=").append(getAuthorities())
        .append('}').toString();
  }
}