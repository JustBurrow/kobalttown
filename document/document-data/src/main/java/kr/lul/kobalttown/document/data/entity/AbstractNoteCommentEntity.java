package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.mapping.AccountMapping;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.data.mapping.NoteMapping;
import kr.lul.kobalttown.document.data.mapping.NoteSnapshotMapping;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.domain.NoteSnapshot;
import kr.lul.support.spring.data.jpa.entiy.CreatableEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;
import static kr.lul.common.util.Arguments.*;
import static kr.lul.kobalttown.document.data.mapping.NoteMapping.FK_NOTE_PK_ACCOUNT;
import static kr.lul.kobalttown.document.data.mapping.NoteMapping.FK_NOTE_PK_ACCOUNT_COLUMNS;
import static kr.lul.kobalttown.document.data.mapping.SharedNoteCommentMapping.*;

/**
 * @author justburrow
 * @since 2020/03/16
 */
//@Entity(name = ENTITY)
@MappedSuperclass
@Table(name = TABLE,
    indexes = {@Index(name = FK_NOTE_PK_ACCOUNT, columnList = FK_NOTE_PK_ACCOUNT_COLUMNS),
        @Index(name = FK_NOTE_COMMENT_PK_NOTE_SNAPSHOT, columnList = FK_NOTE_COMMENT_PK_NOTE_SNAPSHOT_COLUMNS),
        @Index(name = IDX_NOTE_COMMENT_RECENT, columnList = IDX_NOTE_COMMENT_RECENT_COLUMNS)})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER, name = COL_DISCRIMINATOR)
public abstract class AbstractNoteCommentEntity extends CreatableEntity implements NoteComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = COL_ID, nullable = false, insertable = false, updatable = false)
  protected long id;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = COL_AUTHOR, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = FK_NOTE_PK_ACCOUNT), referencedColumnName = AccountMapping.COL_ID)
  protected Account author;
  @ManyToOne(targetEntity = NoteEntity.class)
  @JoinColumn(name = COL_NOTE, nullable = false, insertable = false, updatable = false,
      foreignKey = @ForeignKey(NO_CONSTRAINT), referencedColumnName = NoteMapping.COL_ID)
  protected Note note;
  @Column(name = COL_VERSION, nullable = false, insertable = false, updatable = false)
  protected int version;
  @ManyToOne(targetEntity = NoteSnapshotEntity.class)
  @JoinColumns(value = {@JoinColumn(name = COL_NOTE, nullable = false, updatable = false,
      referencedColumnName = NoteSnapshotMapping.COL_NOTE),
      @JoinColumn(name = COL_VERSION, nullable = false, updatable = false,
          referencedColumnName = NoteSnapshotMapping.COL_VERSION)},
      foreignKey = @ForeignKey(name = FK_NOTE_COMMENT_PK_NOTE_SNAPSHOT))
  protected NoteSnapshot snapshot;
  @Column(name = COL_BODY, nullable = false, updatable = false)
  protected String body;

  public AbstractNoteCommentEntity() { // JPA only
  }

  public AbstractNoteCommentEntity(final Account author, final Note note, final String body, final Instant createdAt) {
    this(author, note.now(), body, createdAt);
  }

  public AbstractNoteCommentEntity(final Account author, final NoteSnapshot snapshot, final String body, final Instant createdAt) {
    super(createdAt);
    notNull(author, "author");
    positive(author.getId(), "author.id");
    typeOf(author, AccountEntity.class, "author");
    notNull(snapshot, "snapshot");
    typeOf(snapshot, NoteSnapshotEntity.class, "snapshot");
    notEmpty(body, "body");
    ae(createdAt, snapshot.getCreatedAt(), "createdAt");

    this.author = author;
    this.note = snapshot.getNote();
    this.version = snapshot.getVersion();
    this.snapshot = snapshot;
    this.body = body;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.NoteComment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public Account getAuthor() {
    return this.author;
  }

  @Override
  public Note getNote() {
    return this.note;
  }

  @Override
  public NoteSnapshot getSnapshot() {
    return this.snapshot;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (0L >= this.id || o == null || getClass() != o.getClass()) return false;
    return this.id == ((AbstractNoteCommentEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }
}

