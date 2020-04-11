package kr.lul.kobalttown.document.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public interface NoteCommentFactory {
  NoteComment create(Context context, Account author, Note note, String body, Instant createdAt);
}
