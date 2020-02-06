package kr.lul.kobalttown.document.domain;

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
  default Class<Note> type() {
    return Note.class;
  }

  @Override
  NoteHistory getHistory(int size, int page);
}
