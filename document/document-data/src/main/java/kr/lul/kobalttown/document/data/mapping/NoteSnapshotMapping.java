package kr.lul.kobalttown.document.data.mapping;

import kr.lul.kobalttown.document.domain.NoteSnapshot;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public abstract class NoteSnapshotMapping {
  public static final String ENTITY = "NoteSnapshot";
  public static final Class<?> ENTITY_TYPE = NoteSnapshot.class;
  public static final String TABLE = "user_note_snapshot";

  public static final String COL_NOTE = "note";
  public static final String COL_VERSION = "version";
  public static final String COL_BODY = "body";
  public static final String COL_CREATED_AT = SavableEntity.COL_CREATED_AT;

  public static final String FK_NOTE_SNAPSHOT_PK_NOTE = "fk_note_snapshot_pk_note";

  protected NoteSnapshotMapping() {
    throw new UnsupportedOperationException();
  }
}
