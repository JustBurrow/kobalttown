package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Creatable;
import kr.lul.common.util.SimpleString;
import kr.lul.common.util.UniqueIdentity;
import kr.lul.kobalttown.account.domain.Account;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import static java.lang.String.format;
import static kr.lul.common.util.Texts.head;
import static kr.lul.kobalttown.document.domain.CommentDiscriminater.COMMENT;
import static kr.lul.kobalttown.document.domain.Document.URI_HOST;
import static kr.lul.kobalttown.document.domain.Document.URI_SCHEME;

/**
 * {@link Document}의 댓글.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Comment extends Creatable<Instant>, SimpleString, UniqueIdentity {
  Class<? extends Comment> type();

  default CommentDiscriminater discriminater() {
    return COMMENT;
  }

  long getId();

  /**
   * @return 대상 도큐먼트.
   */
  Document getDocument();

  /**
   * @return 댓글이 달린 시점의 도큐먼트.
   */
  Snapshot getSnapshot();

  /**
   * @return 댓글 작성자.
   */
  Account getAuthor();

  /**
   * @return 댓글 내용.
   */
  String getBody();

  /**
   * 댓글 삭제.
   *
   * @param deleteAt 삭제 시각.
   */
  void delete(Instant deleteAt);

  /**
   * @return 삭제된 시각. 삭제되지 않았으면 {@code null}.
   */
  Instant getDeletedAt();

  /**
   * @return 삭제되었으면 {@code true}.
   */
  default boolean isDeleted() {
    return null != getDeletedAt();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.SimpleString
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String toSimpleString() {
    return format("(%d, %s)", getId(), head(getBody(), 50));
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.UniqueIdentity
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default URI uri() {
    try {
      return new URI(URI_SCHEME, URI_HOST, format("/%s/%d", type().getCanonicalName(), getId()), null);
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
