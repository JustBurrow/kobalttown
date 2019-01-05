package kr.lul.common.util;

/**
 * ID를 사용해 구분 가능한 오브젝트를 위한 인터페이스.
 *
 * @author justburrow
 * @since 2018. 9. 24.
 */
public interface Identifiable<ID extends Identifier> {
  /**
   * @return 오브젝트 ID
   */
  ID getId();
}
