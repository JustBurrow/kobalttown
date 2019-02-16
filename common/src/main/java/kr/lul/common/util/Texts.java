package kr.lul.common.util;

import static java.lang.String.format;

/**
 * 문자열 유틸리티.
 *
 * @author justburrow
 * @since 2018-12-25
 */
public abstract class Texts {
  /**
   * 문자열이 {@code null}이 아니면 작은 따옴표로 묶어서 반환한다.
   *
   * @param text 원본 문자열.
   *
   * @return 작은 따옴표로 묶은 {@link String} 혹은 {@code null}.
   */
  public static String singleQuote(CharSequence text) {
    return null == text
        ? null
        : format("'%s'", text);
  }

  /**
   * 문자열이 {@code null}이 아니면 큰 따옴표로 묶어서 반환한다.
   *
   * @param text 원본 문자열.
   *
   * @return 큰 따옴표로 묶은 {@link String} 혹은 {@code null}.
   */
  public static String doubleQuote(CharSequence text) {
    return null == text
        ? null
        : format("\"%s\"", text);
  }

  private Texts() {
    throw new UnsupportedOperationException();
  }
}