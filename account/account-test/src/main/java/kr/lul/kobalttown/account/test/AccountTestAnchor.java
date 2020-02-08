package kr.lul.kobalttown.account.test;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/02/08
 */
public class AccountTestAnchor extends Anchor {
  public static final Package PACKAGE = AccountTestAnchor.class.getPackage();
  public static final String PACKAGE_NAME = AccountTestAnchor.class.getPackageName();

  protected AccountTestAnchor() {
    throw new UnsupportedOperationException();
  }
}
