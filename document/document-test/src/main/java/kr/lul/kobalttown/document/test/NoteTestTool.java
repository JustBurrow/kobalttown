package kr.lul.kobalttown.document.test;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;

import java.time.Instant;

import static kr.lul.kobalttown.document.domain.NoteUtil.body;

/**
 * 테스트용 {@link Note} 생성 도구.
 *
 * @author justburrow
 * @since 2020/02/11
 */
public interface NoteTestTool {
  /**
   * @return 임의의 데이터를 가진 노트.
   */
  default Note note() {
    return note(null, body(), null);
  }

  /**
   * 지정한 데이터를 가진 노트.
   *
   * @param author    작성자. {@code null} 이면 임의의 작성자를 사용.
   * @param body      {@link null}이면 임으의 본문을 사용.
   * @param createdAt {@link null}이면 현재 시각을 사용.
   *
   * @return 지정한 데이터 혹은 기본값을 가진 노트.
   */
  Note note(Account author, String body, Instant createdAt);
}
