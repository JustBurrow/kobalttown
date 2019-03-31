package kr.lul.kobalttown.common.api;

import java.util.Map;
import java.util.Set;

/**
 * API 정의.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public interface Api {
  /**
   * @return 동사.
   */
  Verb verb();

  /**
   * @return 이름.
   */
  String name();

  /**
   * @return 입력값.
   */
  Map<String, Class> input();

  /**
   * @return 입력값의 이름.
   */
  Set<String> inputNames();

  /**
   * @return 출력값.
   */
  Map<String, Class> output();

  /**
   * @return 출력값의 이름.
   */
  Set<String> outputNames();
}