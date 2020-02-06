package kr.lul.kobalttown.document.data;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public class DocumentDataAnchor extends Anchor {
  public static final Package PACKAGE = DocumentDataAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentDataAnchor.class.getPackageName();

  protected DocumentDataAnchor() {
    throw new UnsupportedOperationException();
  }
}
