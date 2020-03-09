package kr.lul.kobalttown.account.data;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019/11/23
 */

public abstract class AccountDataAnchor extends Anchor {
  public static final Package PACKAGE = AccountDataAnchor.class.getPackage();
  public static final String PACKAGE_NAME = AccountDataAnchor.class.getPackageName();

  protected AccountDataAnchor() {
    throw new UnsupportedOperationException();
  }
}
