package kr.lul.kobalttown.document.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Pagination;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;

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
   * 노트 목록.
   *
   * @param context 컨텍스트.
   * @param page    페이지. 0-based.
   * @param limit   한 페이지 최대 노트 수.
   *
   * @return 노트 목록.
   */
  Pagination<Note> list(Context context, int page, int limit);

  NoteComment create(Context context, NoteComment comment);
}
