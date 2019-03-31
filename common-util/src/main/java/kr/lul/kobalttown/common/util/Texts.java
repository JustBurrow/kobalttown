package kr.lul.kobalttown.common.util;

import static java.lang.String.format;

/**
 * 문자열 유틸리티.
 *
 * @author justburrow
 * @since 2019-02-27
 */
public class Texts {
  /**
   * 문자열을 따옴표로 묶는다.
   *
   * @param text 원본 문자열.
   *
   * @return {@code null}이면 {@code null}, {@code null}이 아니면 따옴표로 묶은 문자열을 반환한다.
   */
  public static String singleQuote(String text) {
    return null == text
        ? null
        : format("'%s'", text);
  }

  /**
   * 문자열을 쌍따옴표로 묶는다.
   *
   * @param text 원본 문자열.
   *
   * @return {@code null}이면 {@code null}, {@code null}이 아니면 쌍따옴표로 묶은 문자열을 반환한다.
   */
  public static String doubleQuote(String text) {
    return null == text
        ? null
        : format("\"%s\"", text);
  }
}