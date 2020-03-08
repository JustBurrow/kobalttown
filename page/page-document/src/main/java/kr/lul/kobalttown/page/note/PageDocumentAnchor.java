package kr.lul.kobalttown.page.note;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/03/08
 */
public abstract class PageDocumentAnchor extends Anchor {
  public static final Package PACKAGE = PageDocumentAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PageDocumentAnchor.class.getPackageName();

  protected PageDocumentAnchor() {
    throw new UnsupportedOperationException();
  }
}
