package kr.lul.kobalttown.support.spring.web;

import kr.lul.kobalttown.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019-03-04
 */
public final class SupportSpringWebAnchor implements Anchor {
  public static final Package PACKAGE = SupportSpringWebAnchor.class.getPackage();
  public static final String PACKAGE_NAME = SupportSpringWebAnchor.class.getPackageName();

  public SupportSpringWebAnchor() {
    throw new UnsupportedOperationException();
  }
}
