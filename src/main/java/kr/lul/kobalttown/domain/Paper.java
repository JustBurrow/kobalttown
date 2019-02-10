package kr.lul.kobalttown.domain;

import java.nio.file.Path;

/**
 * 코발트 타운으로 서비스하는 웹 문서의 단위.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface Paper {
  String DEFAULT_THEME = "basic";

  /**
   * 일련번호를 사용하는 ID.
   *
   * @return 페이퍼 ID.
   */
  int getId();

  /**
   * 페이퍼 경로. 유일값이기 때문에 ID로 사용 가능함.
   *
   * @return 페이퍼 경로.
   */
  Path getPath();

  /**
   * @return 테마 이름.
   */
  String getTheme();
}