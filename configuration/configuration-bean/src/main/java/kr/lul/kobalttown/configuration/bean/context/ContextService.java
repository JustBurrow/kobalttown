package kr.lul.kobalttown.configuration.bean.context;

import kr.lul.common.data.Context;

/**
 * 현재의 작업 흐름의 정보를 제공한다.
 *
 * @author justburrow
 * @since 2019/12/07
 */
public interface ContextService {
  /**
   * @return 신규 컨텍스트.
   */
  Context issue();

  /**
   * @return 현재 컨텍스트.
   */
  Context get();

  /**
   * 현재 컨텍스트를 삭제한다.
   *
   * @return 컨텍스트를 삭제했으면 {@code true}.
   */
  boolean clear();
}
