package kr.lul.kobalttown.document.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;

import java.time.Instant;

/**
 * {@link Note} 인스턴스 팩토리.
 *
 * @author justburrow
 * @since 2020/02/09
 */
public interface NoteFactory {
  /**
   * {@link Note} 인스턴스 생성.
   *
   * @param author    작성자.
   * @param body      본문.
   * @param createdAt 생성 시각.
   *
   * @return 노트.
   */
  Note create(Context context, Account author, String body, Instant createdAt);
}
