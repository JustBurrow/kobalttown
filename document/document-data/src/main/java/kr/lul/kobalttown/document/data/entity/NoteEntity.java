package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteHistory;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public class NoteEntity extends SavableEntity implements Note {
  @Override
  public String getBody() {
    return null;
  }

  @Override
  public long getId() {
    return 0;
  }

  @Override
  public int getVersion() {
    return 0;
  }

  @Override
  public NoteHistory getHistory(final int size, final int page) {
    return null;
  }
}
