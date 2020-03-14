package kr.lul.kobalttown.document.service;

import kr.lul.common.data.Page;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.service.params.*;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public interface NoteService {
  Note create(CreateNoteParams params);

  Note read(ReadNoteParams params);

  Page<Note> list(ListNoteParams params);

  Note update(UpdateNoteParams params);

  void delete(DeleteNoteParams params);
}
