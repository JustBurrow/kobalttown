package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.document.data.mapping.NoteMapping;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteSnapshot;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.*;
import static kr.lul.kobalttown.document.data.mapping.NoteSnapshotMapping.*;

/**
 * @author justburrow
 * @since 2020/03/04
 */
@Entity(name = ENTITY)
@Table(name = TABLE)
public class NoteSnapshotEntity implements NoteSnapshot {
  @Embeddable
  public static class NoteSnapshotId implements NoteSnapshot.Id {
    @Column(name = COL_NOTE, nullable = false, updatable = false)
    private long document;
    @Column(name = COL_VERSION, nullable = false, updatable = false)
    private int version;

    /**
     * JPA only
     */
    public NoteSnapshotId() {
    }

    public NoteSnapshotId(final long document, final int version) {
      notNegative(document, "document");
      notNegative(version, "version");

      this.document = document;
      this.version = version;
    }

    @Override
    public long document() {
      return this.document;
    }

    @Override
    public int version() {
      return this.version;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass() || 0L >= this.document || 0 > this.version) return false;
      final NoteSnapshotId that = (NoteSnapshotId) o;
      return this.document == that.document &&
                 this.version == that.version;
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.document, this.version);
    }

    @Override
    public String toString() {
      return format("(%d, %d)", this.document, this.version);
    }
  }

  @EmbeddedId
  private NoteSnapshotId id;
  @ManyToOne(targetEntity = NoteEntity.class)
  @JoinColumn(name = COL_NOTE, nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = FK_NOTE_SNAPSHOT_PK_NOTE), referencedColumnName = NoteMapping.COL_ID)
  @MapsId(COL_NOTE)
  private Note note;
  @Column(name = COL_BODY, nullable = false, updatable = false)
  private String body;
  @Column(name = COL_CREATED_AT, nullable = false, updatable = false)
  private Instant createdAt;
  @Column(name = COL_DELETED_AT, insertable = false)
  private Instant deletedAt;

  public NoteSnapshotEntity() { // JPA only
  }

  /**
   * {@link NoteEntity} only.
   *
   * @param note      노트.
   * @param createdAt 노트 갱신 시각.
   */
  public NoteSnapshotEntity(final NoteEntity note, final Instant createdAt) {
    notNull(note, "note");
    notNull(createdAt, "createdAt");

    this.id = new NoteSnapshotId(note.getId(), note.getVersion());
    this.note = note;
    this.createdAt = createdAt;

    setBody(note.getBody());
  }

  void setBody(final String body) {
    notNull(body, "body");
    this.body = body;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.NoteSnapshot
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public NoteSnapshotId getId() {
    return this.id;
  }

  @Override
  public int getVersion() {
    return this.id.version;
  }

  @Override
  public Note getNote() {
    return this.note;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  @Override
  public Instant getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public Instant getDeleted() {
    return this.deletedAt;
  }

  @Override
  public void delete(final Instant deletedAt) throws IllegalStateException {
    notNull(deletedAt, "deletedAt");
    ae(deletedAt, this.deletedAt, "deletedAt");

    if (null != this.deletedAt)
      throw new IllegalStateException(format("already deleted note snapshot : id=%s, deletedAt=%s", this.id, this.deletedAt));
    else
      this.deletedAt = deletedAt;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final NoteSnapshotEntity that = (NoteSnapshotEntity) o;
    return this.id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public String toString() {
    return new StringBuilder(NoteSnapshotEntity.class.getSimpleName())
               .append("{id=").append(this.id)
               .append(", note=").append(this.note.toSimpleString())
               .append(", body='").append(this.body).append('\'')
               .append(", createdAt=").append(this.createdAt)
               .append('}').toString();
  }
}
