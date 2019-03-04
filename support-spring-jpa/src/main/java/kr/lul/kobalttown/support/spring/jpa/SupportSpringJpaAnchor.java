package kr.lul.kobalttown.support.spring.jpa;

import kr.lul.kobalttown.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019-03-04
 */
public final class SupportSpringJpaAnchor implements Anchor {
  public static final Package PACKAGE = SupportSpringJpaAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();

  public SupportSpringJpaAnchor() {
    throw new UnsupportedOperationException();
  }
}