package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.kobalttown.account.domain.Account;

import static kr.lul.common.util.Texts.head;

/**
 * 단순 텍스트 도큐먼트.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Note extends Document {
  String ATTR_AUTHOR = "author";
  String ATTR_BODY = "body";

  Validator<Account> AUTHOR_VALIDATOR = OWNER_VALIDATOR;

  int BODY_MAX_LENGTH = 1_000;

  Validator<String> BODY_VALIDATOR = body -> {
    if (null == body) {
      throw new ValidationException(ATTR_BODY, null, "body is null.");
    } else if (BODY_MAX_LENGTH < body.length()) {
      throw new ValidationException(ATTR_BODY, head(body, 20), "too long body.");
    }
  };

  /**
   * @return 작성자.
   */
  Account getAuthor();

  /**
   * @return 내용.
   */
  String getBody();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Document
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Class<Note> type() {
    return Note.class;
  }

  @Override
  default Account getOwner() {
    return getAuthor();
  }

  @Override
  NoteHistory history(int size, int page);
}
