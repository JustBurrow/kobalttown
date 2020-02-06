package kr.lul.kobalttown.document.domain;

import static java.lang.String.format;

/**
 * @author justburrow
 * @since 2020/01/29
 */
public interface NoteRecomment extends Recomment {
  Note getNote();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Recomment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String getKey() {
    return format("%s.%d", NoteRecomment.class, getId());
  }

  @Override
  default Note getDocument() {
    return getNote();
  }

  @Override
  NoteSnapshot getSnapshot();
}
