package kr.lul.kobalttown.document.data.mapping;

import kr.lul.kobalttown.document.domain.Note;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public abstract class NoteMapping {
  public static final String ENTITY = "Note";
  public static final Class<?> ENTITY_TYPE = Note.class;
  public static final String TABLE = "user_note";

  public static final String COL_ID = "id";
  public static final String COL_VERSION = "version";
  public static final String COL_AUTHOR = "author";
  public static final String COL_BODY = "body";
  public static final String COL_CREATED_AT = SavableEntity.COL_CREATED_AT;
  public static final String COL_UPDATED_AT = SavableEntity.COL_UPDATED_AT;
  public static final String COL_DELETED_AT = "deleted_at";

  public static final String FK_NOTE_PK_ACCOUNT = "fk_note_pk_account";
  public static final String FK_NOTE_PK_ACCOUNT_COLUMNS = COL_AUTHOR + " ASC";

  public static final String IDX_NOTE_DELETED = "idx_note_deleted";
  public static final String IDX_NOTE_DELETED_COLUMNS = COL_DELETED_AT + " ASC";

  protected NoteMapping() {
    throw new UnsupportedOperationException();
  }
}
