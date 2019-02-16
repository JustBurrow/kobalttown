package kr.lul.kobalttown.web.controller;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019-01-04
 */
public class WebControllerAnchor implements Anchor {
  public static final Package PACKAGE = WebControllerAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();

  protected WebControllerAnchor() {
    throw new UnsupportedOperationException();
  }
}
