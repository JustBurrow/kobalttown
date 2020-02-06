package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.SimpleString;
import kr.lul.common.util.UniqueIdentity;

import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static kr.lul.kobalttown.document.domain.CommentDiscriminater.COMMENT;
import static kr.lul.kobalttown.document.domain.Document.URI_HOST;
import static kr.lul.kobalttown.document.domain.Document.URI_SCHEME;

/**
 * {@link Document}의 댓글.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Comment extends SimpleString, UniqueIdentity {
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
   * @return 댓글 내용.
   */
  String getBody();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.SimpleString
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String toSimpleString() {
    return getBody();
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
