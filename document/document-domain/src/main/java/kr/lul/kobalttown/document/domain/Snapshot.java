package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Creatable;
import kr.lul.common.util.SimpleString;
import kr.lul.common.util.UniqueIdentity;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import static java.lang.String.format;

/**
 * 도큐먼트의 특정 버전.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Snapshot extends Creatable<Instant>, SimpleString, UniqueIdentity {
  interface Id extends Serializable {
    long document();

    int version();
  }

  Class<? extends Document> type();

  Id getId();

  /**
   * @return 스냅샷의 버전. 0-based.
   */
  int getVision();

  /**
   * @return 대상 도큐먼트.
   */
  Document getDocument();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.SimpleString
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default String toSimpleString() {
    return uri().toString();
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.UniqueIdentity
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default URI uri() {
    try {
      return new URI(Document.URI_SCHEME, Document.URI_HOST,
          format("/%s/%d/%d", type().getCanonicalName(), getId().document(), getId().version()),
          null);
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
