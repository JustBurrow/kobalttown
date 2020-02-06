package kr.lul.kobalttown.document.domain;

import static java.lang.String.format;

/**
 * 단순 텍스트 도큐먼트.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Note extends Document {
  /**
   * @return 내용.
   */
  String getBody();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Document
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String getKey() {
    return format("%s.%d", Note.class.getCanonicalName(), getId());
  }

  @Override
  History getHistory(int size, int page);
}
