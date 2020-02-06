package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Savable;
import kr.lul.common.util.SimpleString;
import kr.lul.common.util.UniqueIdentity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;

import static java.lang.String.format;

/**
 * 기본 자료형.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Document extends Savable<Instant>, SimpleString, UniqueIdentity {
  String URI_SCHEME = "kd";
  String URI_HOST = "kobalttown";

  /**
   * @return 도큐먼트 종류.
   */
  Class<? extends Document> type();

  long getId();

  /**
   * @return 현재 버전. 0-based.
   */
  int getVersion();

  /**
   * @param size 한 페이지에 들어갈 스냅샷 갯수.
   * @param page 0-based.
   *
   * @return 버전 히스토리.
   */
  History getHistory(int size, int page);

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
      return new URI(URI_SCHEME, URI_HOST, format("/%s/%d", type().getCanonicalName(), getId()), null);
    } catch (final URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
