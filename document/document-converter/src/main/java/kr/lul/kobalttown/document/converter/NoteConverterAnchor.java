package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public class NoteConverterAnchor extends Anchor {
  public static final Package PACKAGE = NoteConverterAnchor.class.getPackage();
  public static final String PACKAGE_NAME = NoteConverterAnchor.class.getPackageName();

  protected NoteConverterAnchor() {
    throw new UnsupportedOperationException();
  }
}
