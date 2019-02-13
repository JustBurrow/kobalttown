package kr.lul.kobalttown.configuration;

import kr.lul.common.util.Anchor;

/**
 * @author justburrow
 * @since 2019-01-04
 */
public class ConfigurationAnchor implements Anchor {
  public static final Package PACKAGE = ConfigurationAnchor.class.getPackage();
  public static final String PACKAGE_NAME = PACKAGE.getName();

  protected ConfigurationAnchor() {
    throw new UnsupportedOperationException();
  }
}
