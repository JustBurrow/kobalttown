package kr.lul.kobalttown.document.test;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class DocumentTestAnchor extends Anchor {
  public static final Package PACKAGE = DocumentTestAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentTestAnchor.class.getPackageName();

  protected DocumentTestAnchor() {
    throw new UnsupportedOperationException();
  }
}
