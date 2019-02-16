package kr.lul.kobalttown.loader;

import kr.lul.kobalttown.domain.GenericPaperLoader;
import kr.lul.kobalttown.domain.Papermark;

/**
 * 페이퍼로더 딜리게이터.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface PaperLoaderDelegator extends GenericPaperLoader {
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.domain.PaperLoader
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default boolean isSupported(Class<? extends Papermark> clz) {
    return true;
  }
}