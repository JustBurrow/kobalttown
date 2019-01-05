package kr.lul.kobalttown.loader;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class LoaderAnchor implements kr.lul.common.util.Anchor {
  public static final Package PACKAGE = LoaderAnchor.class.getPackage();
  public static final String PACKAGE_NAME = LoaderAnchor.class.getPackageName();

  protected LoaderAnchor() {
    throw new UnsupportedOperationException();
  }
}
