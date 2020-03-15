package kr.lul.kobalttown.document.web;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public abstract class DocumentWebAnchor extends Anchor {
  public static final Package PACKAGE = DocumentWebAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentWebAnchor.class.getPackageName();

  protected DocumentWebAnchor() {
    throw new UnsupportedOperationException();
  }
}
