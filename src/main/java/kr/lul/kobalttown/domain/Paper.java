package kr.lul.kobalttown.domain;

/**
 * 코발트 타운으로 서비스하는 웹 문서의 단위.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface Paper {
  String DEFAULT_THEME = "basic";

  /**
   * @return 테마 이름.
   */
  String getThemem();
}