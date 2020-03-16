package kr.lul.kobalttown.document.data.entity;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.domain.NoteRecomment;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

import static kr.lul.common.util.Arguments.typeOf;
import static kr.lul.common.util.Texts.head;
import static kr.lul.common.util.Texts.singleQuote;
import static kr.lul.kobalttown.document.data.mapping.NoteRecommentMapping.COL_COMMENT;
import static kr.lul.kobalttown.document.data.mapping.NoteRecommentMapping.ENTITY;

/**
 * @author justburrow
 * @since 2020/03/16
 */
@Entity(name = ENTITY)
@DiscriminatorValue("1")
public class NoteRecommentEntity extends AbstractNoteCommentEntity implements NoteRecomment {
  @ManyToOne(targetEntity = NoteCommentEntity.class)
  @JoinColumn(name = COL_COMMENT, nullable = false, updatable = false)
  private NoteComment comment;

  public NoteRecommentEntity() { // JPA only
  }

  public NoteRecommentEntity(final Account author, final NoteComment comment, final String body, final Instant createdAt) {
    super(author, comment.getSnapshot(), body, createdAt);
    typeOf(comment, NoteCommentEntity.class, "comment");

    this.comment = comment;
  }


  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.NoteRecomment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public NoteComment getComment() {
    return this.comment;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // java.lang.Object
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String toString() {
    return new StringBuilder(getClass().getSimpleName())
               .append("{id=").append(this.id)
               .append(", author=").append(this.author.toSimpleString())
               .append(", snapshot=").append(this.snapshot)
               .append(", comment=").append(this.comment.toSimpleString())
               .append(", body=").append(singleQuote(head(this.body, 50)))
               .append(", createdAt=").append(this.createdAt)
               .append('}').toString();
  }
}
