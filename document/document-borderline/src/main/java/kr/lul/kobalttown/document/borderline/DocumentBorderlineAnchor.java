package kr.lul.kobalttown.document.borderline;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public abstract class DocumentBorderlineAnchor extends Anchor {
  public static final Package PACKAGE = DocumentBorderlineAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentBorderlineAnchor.class.getPackageName();

  protected DocumentBorderlineAnchor() {
    throw new UnsupportedOperationException();
  }
}
