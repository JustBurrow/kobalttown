package kr.lul.kobalttown.document.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.data.entity.NoteCommentEntity;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/22
 */
@Service
class NoteCommentFactoryImpl implements NoteCommentFactory {
  protected static final Logger log = getLogger(NoteCommentFactoryImpl.class);

  @Override
  public NoteComment create(final Context context, final Account author, final Note note, final String body,
      final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, author={}, note={}, body={}, createdAt={}", context, author, note, body, createdAt);

    final NoteCommentEntity comment = new NoteCommentEntity(author, note, body, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, comment);
    return comment;
  }
}
