package kr.lul.kobalttown.support.spring.boot;

import kr.lul.kobalttown.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019-03-15
 */
public class SupportSpringBootAnchor implements Anchor {
  public static final Package PACKAGE = SupportSpringBootAnchor.class.getPackage();
  public static final String PACKAGE_NAME = SupportSpringBootAnchor.class.getPackageName();

  protected SupportSpringBootAnchor() {
    throw new UnsupportedOperationException();
  }
}