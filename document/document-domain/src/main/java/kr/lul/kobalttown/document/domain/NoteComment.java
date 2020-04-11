package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.kobalttown.account.domain.Account;

import static java.lang.String.format;

/**
 * {@link Note}에 달리는 댓글.
 *
 * @author justburrow
 * @since 2020/01/29
 */
public interface NoteComment extends Comment {
  String ATTR_NOTE = "note";
  String ATTR_BODY = "body";

  Validator<Account> AUTHOR_VALIDATOR = author -> {
    if (null == author) {
      throw new ValidationException("author", null, "author is null.");
    } else if (0L >= author.getId()) {
      throw new ValidationException("author", author, "author is not persisted : " + author);
    }
  };

  int BODY_MAX_LENGTH = 4096;

  Validator<String> BODY_VALIDATOR = body -> {
    if (null == body) {
      throw new ValidationException("body", null, "body is null.");
    } else if (body.isEmpty()) {
      throw new ValidationException("body", body, "body is empty.");
    } else if (BODY_MAX_LENGTH < body.length()) {
      throw new ValidationException("body", body,
          format("too long body : body.length=%d, max=%d", body.length(), BODY_MAX_LENGTH));
    }
  };

  @Override
  default Class<NoteComment> type() {
    return NoteComment.class;
  }

  Note getNote();

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.domain.Comment
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Note getDocument() {
    return getNote();
  }

  @Override
  NoteSnapshot getSnapshot();
}
