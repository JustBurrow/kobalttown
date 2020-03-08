package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.account.data.entity.AccountEntity;
import kr.lul.kobalttown.account.data.mapping.AccountMapping;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.data.mapping.NoteSnapshotMapping;
import kr.lul.kobalttown.document.domain.*;
import kr.lul.support.spring.data.jpa.entiy.SavableEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.*;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.document.data.mapping.NoteMapping.*;

/**
 * @author justburrow
 * @since 2020/02/06
 */
@Entity(name = ENTITY)
@Table(name = TABLE)
public class NoteEntity extends SavableEntity implements Note {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = COL_ID, nullable = false, insertable = false, updatable = false)
  private long id;
  @Column(name = COL_VERSION, nullable = false)
  private int version;
  @ManyToOne(targetEntity = AccountEntity.class)
  @JoinColumn(name = COL_AUTHOR,
      nullable = false,
      updatable = false,
      foreignKey = @ForeignKey(name = FK_NOTE_PK_ACCOUNT),
      referencedColumnName = AccountMapping.COL_ID)
  private Account author;
  @Column(name = COL_BODY, nullable = false)
  private String body;
  @OneToMany(targetEntity = NoteSnapshotEntity.class, mappedBy = NoteSnapshotMapping.COL_NOTE, cascade = CascadeType.PERSIST)
  @OrderBy(NoteSnapshotMapping.COL_VERSION + " ASC")
  private List<NoteSnapshot> history = new ArrayList<>();

  public NoteEntity() { // JPA only
  }

  public NoteEntity(final Account author, final String body, final Instant createdAt) {
    super(createdAt);
    notNull(author, "author");
    positive(author.getId(), "author.id");
    notNull(body, "body");

    if (author.getCreatedAt().isAfter(createdAt))
      throw new IllegalArgumentException(format("author.createdAt is after than createdAt : author.createdAt=%s, createdAt=%s",
          author.getCreatedAt(), createdAt));

    this.author = author;
    this.body = body;
  }

  @PostPersist
  private void postPersist() {
    final NoteSnapshotEntity init = new NoteSnapshotEntity(this, this.createdAt);
    init.setBody(this.body);
    this.history.add(init);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Note
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public int getVersion() {
    return this.version;
  }

  @Override
  public Account getAuthor() {
    return this.author;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  @Override
  public NoteUpdater updater(final Instant updatedAt) {
    notNull(updatedAt, "updatedAt");
    return null;
  }

  @Override
  public History<NoteSnapshot> history(final int size, final int page) {
    positive(size, "size");
    notNegative(page, "page");

    final List<NoteSnapshot> content;
    final int from = size * page;
    if (this.history.size() < from) {
      content = List.of();
    } else {
      final int to = Math.min(from + size, this.history.size());
      content = this.history.subList(from, to);
    }
    return new HistoryImpl<>(size, page, this.history.size(), content);
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (0L >= this.id || o == null || getClass() != o.getClass()) return false;
    return this.id == ((NoteEntity) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringBuilder(NoteEntity.class.getSimpleName())
               .append("{id=").append(this.id)
               .append(", version=").append(this.version)
               .append(", author=").append(this.author.toSimpleString())
               .append(", body=").append(singleQuote(head(this.body, 10)))
               .append(", createdAt=").append(this.createdAt)
               .append(", updatedAt=").append(this.updatedAt)
               .append('}').toString();
  }
}