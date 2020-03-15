package kr.lul.kobalttown.document.domain;

/**
 * {@link Note}에 달리는 댓글.
 *
 * @author justburrow
 * @since 2020/01/29
 */
public interface NoteComment extends Comment {
  @Override
  default Class<NoteComment> type() {
    return NoteComment.class;
  }

  Note getNote();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Comment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Note getDocument() {
    return getNote();
  }

  @Override
  NoteSnapshot getSnapshot();
}
