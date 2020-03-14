package kr.lul.kobalttown.document.borderline;

import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.document.borderline.command.CreateNoteCmd;
import kr.lul.kobalttown.document.borderline.command.DeleteNoteCmd;
import kr.lul.kobalttown.document.borderline.command.ReadNoteCmd;
import kr.lul.kobalttown.document.borderline.command.UpdateNoteCmd;
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

  /**
   * 노트 수정.
   *
   * @param cmd 수정할 노트 정보.
   *
   * @return 수정된 노트.
   *
   * @throws ValidationException 수정할 노트가 없거나 수정에 실패했을 때.
   */
  NoteDetailDto update(UpdateNoteCmd cmd) throws ValidationException;

  /**
   * 노트 삭제.
   *
   * @param cmd 삭제할 노트 정보.
   */
  void delete(DeleteNoteCmd cmd);
}
