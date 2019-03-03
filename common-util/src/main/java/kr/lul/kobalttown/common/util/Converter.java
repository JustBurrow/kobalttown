package kr.lul.kobalttown.common.util;

import java.util.Set;

/**
 * @param <S> source
 *
 * @author justburrow
 * @since 2019-02-28
 */
public interface Converter<S> {
  /**
   * @return 지원하는 타겟 타입.
   */
  Set<Class> supportsTarget();

  /**
   * @param source     원본 오브젝트.
   * @param targetType 타겟 타입.
   * @param <T>        타겟 타입.
   *
   * @return 타겟 오브젝트.
   */
  <T> T convert(S source, Class<T> targetType);
}