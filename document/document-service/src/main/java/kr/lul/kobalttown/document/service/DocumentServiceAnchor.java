package kr.lul.kobalttown.document.service;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public class DocumentServiceAnchor extends Anchor {
  public static final Package PACKAGE = DocumentServiceAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentServiceAnchor.class.getPackageName();

  protected DocumentServiceAnchor() {
    throw new UnsupportedOperationException();
  }
}
