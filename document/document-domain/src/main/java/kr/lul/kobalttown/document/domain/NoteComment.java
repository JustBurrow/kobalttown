package kr.lul.kobalttown.document.domain;

import static java.lang.String.format;

/**
 * {@link Note}에 달리는 댓글.
 *
 * @author justburrow
 * @since 2020/01/29
 */
public interface NoteComment extends Comment {
  Note getNote();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Comment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String getKey() {
    final Snapshot.Id snapshot = getSnapshot().getId();
    return format("%s.%d", NoteComment.class.getCanonicalName(), getId());
  }

  @Override
  default Note getDocument() {
    return getNote();
  }

  @Override
  NoteSnapshot getSnapshot();
}
