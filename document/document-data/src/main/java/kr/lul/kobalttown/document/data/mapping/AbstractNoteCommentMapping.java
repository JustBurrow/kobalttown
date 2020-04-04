package kr.lul.kobalttown.document.data.mapping;

import kr.lul.support.spring.data.jpa.entiy.CreatableEntity;

/**
 * @author justburrow
 * @since 2020/03/16
 */
public abstract class AbstractNoteCommentMapping {
  public static final String ENTITY = "AbstractNoteComment";
  public static final String TABLE = "user_note_comment";

  public static final String COL_ID = "id";
  public static final String COL_DISCRIMINATOR = "discriminator";
  public static final String COL_AUTHOR = "author";
  public static final String COL_NOTE = "note";
  public static final String COL_VERSION = "version";
  public static final String COL_COMMENT = "comment";
  public static final String COL_BODY = "body";
  public static final String COL_CREATED_AT = CreatableEntity.COL_CREATED_AT;

  public static final String FK_NOTE_COMMENT_PK_ACCOUNT = "fk_note_comment_pk_account";
  public static final String FK_NOTE_COMMENT_PK_ACCOUNT_COLUMNS = COL_AUTHOR + " ASC";

  public static final String FK_NOTE_COMMENT_PK_NOTE_SNAPSHOT = "fk_note_comment_pk_note_snapshot";
  public static final String FK_NOTE_COMMENT_PK_NOTE_SNAPSHOT_COLUMNS = COL_NOTE + " ASC, " + COL_VERSION + " ASC";

  public static final String FK_NOTE_COMMENT_RECOMMENT = "fk_note_comment_recomment";
  public static final String FK_NOTE_COMMENT_RECOMMENT_COLUMNS = COL_COMMENT + " ASC";

  public static final String IDX_NOTE_COMMENT_RECENT = "idx_note_comment_recent";
  public static final String IDX_NOTE_COMMENT_RECENT_COLUMNS = COL_NOTE + " ASC, " + COL_CREATED_AT + " DESC";

  protected AbstractNoteCommentMapping() {
    throw new UnsupportedOperationException();
  }
}
