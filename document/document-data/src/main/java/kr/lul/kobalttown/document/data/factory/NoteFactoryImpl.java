package kr.lul.kobalttown.document.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.kobalttown.document.domain.Note;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/09
 */
@Service
class NoteFactoryImpl implements NoteFactory {
  protected static final Logger log = getLogger(NoteFactoryImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.data.factory.NoteFactory
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Note create(final Context context, final Account author, final String body, final Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, author={}, body={}, createdAt={}", context, author, body, createdAt);
    notNull(author, "author");
    notNull(createdAt, "createdAt");

    final NoteEntity note = new NoteEntity(author, body, createdAt);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, note);
    return note;
  }
}
