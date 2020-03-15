package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2020/01/28
 */
public class DocumentDomainAnchor extends Anchor {
  public static final Package PACKAGE = DocumentDomainAnchor.class.getPackage();
  public static final String PACKAGE_NAME = DocumentDomainAnchor.class.getPackageName();

  protected DocumentDomainAnchor() {
    throw new UnsupportedOperationException();
  }
}
