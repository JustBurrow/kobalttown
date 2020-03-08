package kr.lul.kobalttown.document.service;

import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.service.params.CreateNoteParams;
import kr.lul.kobalttown.document.service.params.ReadNoteParams;

/**
 * @author justburrow
 * @since 2020/02/11
 */
public interface NoteService {
  Note create(CreateNoteParams params);

  Note read(ReadNoteParams params);
}