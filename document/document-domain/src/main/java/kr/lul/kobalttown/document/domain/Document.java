package kr.lul.kobalttown.document.domain;

import kr.lul.common.data.Savable;
import kr.lul.common.util.SimpleString;
import kr.lul.common.util.UniqueIdentity;
import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.kobalttown.account.domain.Account;

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

  String ATTR_ID = "id";
  String ATTR_VERSION = "version";
  String ATTR_OWNER = "owner";
  String ATTR_CREATED_AT = "createdAt";
  String ATTR_UPDATED_AT = "updatedAt";

  Validator<Account> OWNER_VALIDATOR = owner -> {
    if (null == owner) {
      throw new ValidationException(ATTR_OWNER, null, "owner is null.");
    } else if (0L >= owner.getId()) {
      throw new ValidationException(ATTR_OWNER, owner, "owner is not persisted.");
    } else if (!owner.isEnabled()) {
      throw new ValidationException(ATTR_OWNER, owner, "owner is not enabled.");
    }
  };

  /**
   * @return 도큐먼트 종류.
   */
  Class<? extends Document> type();

  /**
   * @return ID.
   */
  long getId();

  /**
   * @return 현재 버전. 0-based.
   */
  int getVersion();

  Account getOwner();

  /**
   * @param size 한 페이지에 들어갈 스냅샷 갯수.
   * @param page 0-based.
   *
   * @return 버전 히스토리.
   */
  History history(int size, int page);

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
