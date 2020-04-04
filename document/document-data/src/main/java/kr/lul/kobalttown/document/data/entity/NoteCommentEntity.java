package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteSnapshot;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.Instant;

import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.document.data.mapping.NoteCommentMapping.ENTITY;

/**
 * @author justburrow
 * @since 2020/03/16
 */
@Entity(name = ENTITY)
@DiscriminatorValue("0")
public class NoteCommentEntity extends AbstractNoteCommentEntity {
  public NoteCommentEntity() { // JPA only
  }

  public NoteCommentEntity(final Account author, final Note note, final String body, final Instant createdAt) {
    super(author, note, body, createdAt);
  }

  public NoteCommentEntity(final Account author, final NoteSnapshot snapshot, final String body, final Instant createdAt) {
    super(author, snapshot, body, createdAt);
  }

  @Override
  public long getId() {
    return this.id;
  }

  @Override
  public String toString() {
    return new StringBuilder(getClass().getSimpleName())
               .append("{id=").append(this.id)
               .append(", author=").append(this.author.toSimpleString())
               .append(", snapshot=").append(this.snapshot)
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", createdAt=").append(this.createdAt)
               .append(", deletedAt=").append(this.deletedAt)
               .append('}').toString();
  }
}
