package kr.lul.kobalttown.document.service;

import kr.lul.common.data.Pagination;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.service.params.*;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public interface NoteService {
  Note create(CreateNoteParams params);

  Note read(ReadNoteParams params);

  Pagination<Note> list(ListNoteParams params);

  Note update(UpdateNoteParams params);

  void delete(DeleteNoteParams params);

  NoteComment comment(CreateNoteCommentParams params);

  void delete(DeleteNoteCommentParams params);
}
