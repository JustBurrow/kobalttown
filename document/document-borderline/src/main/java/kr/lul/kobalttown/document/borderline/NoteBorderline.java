package kr.lul.kobalttown.document.borderline;

import kr.lul.kobalttown.document.borderline.command.CreateNoteCmd;
import kr.lul.kobalttown.document.borderline.command.ReadNoteCmd;
import kr.lul.kobalttown.document.dto.NoteDetailDto;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public interface NoteBorderline {
  /**
   * 신규 노트를 작성한다.
   *
   * @param cmd 노트 내용.
   *
   * @return 신규 노트 정보.
   */
  NoteDetailDto create(CreateNoteCmd cmd);

  /**
   * 노트 읽기.
   *
   * @param cmd 노트 정보.
   *
   * @return 노트. 없으면 {@code null}.
   */
  NoteDetailDto read(ReadNoteCmd cmd);
}
