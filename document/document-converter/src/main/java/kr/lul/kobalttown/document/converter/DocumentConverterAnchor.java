package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public abstract class DocumentConverterAnchor extends Anchor {
  public static final Package PACKAGE = DocumentConverterAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentConverterAnchor.class.getPackageName();

  protected DocumentConverterAnchor() {
    throw new UnsupportedOperationException();
  }
}
