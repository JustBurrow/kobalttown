package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.common.util.Validator;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;
import java.util.List;

import static kr.lul.common.util.Texts.head;

/**
 * 단순 텍스트 도큐먼트.
 *
 * @author justburrow
 * @since 2020/01/28
 */
public interface Note extends DeletableDocument {
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
   * @return 최신 버전의 스냅샷.
   */
  NoteSnapshot now();

  /**
   * @return 내용.
   */
  String getBody();

  /**
   * 노트를 수정할 수 있는 업데이터 오브젝트 요청.
   *
   * @param updatedAt 수정 시각.
   *
   * @return 업데이터 오브젝트.
   */
  NoteUpdater updater(Instant updatedAt);

  /**
   * @return 댓글 목록.
   */
  List<NoteComment> getComments();

  /**
   * 댓글 삭제.
   *
   * @param author  댓글 작성자.
   * @param comment 댓글 ID.
   */
  void deleteComment(Account author, long comment);

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
  History<NoteSnapshot> history(int size, int page);
}
