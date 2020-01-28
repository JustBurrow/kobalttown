package kr.lul.kobalttown.document.domain;

/**
 * @author justburrow
 * @since 2020/01/28
 */
public interface NoteSnapshot extends Snapshot<Note, NoteSnapshot.Id> {
  interface Id extends Snapshot.Id {
    @Override
    default Class<?> type() {
      return NoteSnapshot.class;
    }
  }

  Note getNote();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Snapshot
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  NoteSnapshot.Id getId();

  @Override
  default Note getDocument() {
    return getNote();
  }
}
