package kr.lul.kobalttown.document.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.document.domain.Note;

/**
 * @author justburrow
 * @since 2020/02/09
 */
public interface NoteDao {
  /**
   * 신규 노트를 저장한다.
   *
   * @param context 컨텍스트.
   * @param note    저장할 노트.
   *
   * @return 저장한 노트.
   */
  Note create(Context context, Note note);

  /**
   * 노트를 읽는다.
   *
   * @param context 컨텍스트.
   * @param id      노트 ID.
   *
   * @return 노트. 없으면 {@code null}.
   */
  Note read(Context context, long id);

  /**
   * 노트를 삭제한다.
   *
   * @param context 컨텍스트
   * @param note    삭제할 노트.
   */
  void delete(Context context, Note note);
}
