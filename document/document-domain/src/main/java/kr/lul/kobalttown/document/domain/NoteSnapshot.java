package kr.lul.kobalttown.document.domain;

/**
 * @author justburrow
 * @since 2020/01/28
 */
public interface NoteSnapshot extends Snapshot {
  interface Id extends Snapshot.Id {
  }

  Note getNote();

  String getBody();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Snapshot
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Class<Note> type() {
    return Note.class;
  }

  @Override
  NoteSnapshot.Id getId();

  @Override
  default Note getDocument() {
    return getNote();
  }
}
