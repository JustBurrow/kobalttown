package kr.lul.kobalttown.global.web;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/05/21
 */
public abstract class GlobalWebAnchor extends Anchor {
  public static final Package PACKAGE = GlobalWebAnchor.class.getPackage();
  public static final String PACKAGE_NAME = GlobalWebAnchor.class.getPackageName();

  protected GlobalWebAnchor() {
    throw new UnsupportedOperationException();
  }
}
