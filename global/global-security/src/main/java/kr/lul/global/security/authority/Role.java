package kr.lul.global.security.authority;

/**
 * @author justburrow
 * @since 2019/11/25
 */
public abstract class Role {
  public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
  public static final String ROLE_USER = "ROLE_USER";

  public Role() {
    throw new UnsupportedOperationException();
  }
}
