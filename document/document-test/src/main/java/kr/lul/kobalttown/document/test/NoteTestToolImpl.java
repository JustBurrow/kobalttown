package kr.lul.kobalttown.document.test;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.test.AccountTestTool;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.kobalttown.document.data.repository.NoteRepository;
import kr.lul.kobalttown.document.domain.Note;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static kr.lul.kobalttown.document.domain.NoteUtil.body;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@Service
class NoteTestToolImpl implements NoteTestTool {
  protected static final Logger log = getLogger(NoteTestToolImpl.class);

  @Autowired
  private NoteRepository repository;
  @Autowired
  private AccountTestTool accountTestTool;
  @Autowired
  private TimeProvider timeProvider;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.test.NoteTestTool
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Note note(Account author, String body, Instant createdAt) {
    if (log.isTraceEnabled())
      log.trace("#note args : author={}, body={}, createdAt={}", author, body, createdAt);

    if (null == author)
      author = this.accountTestTool.account();
    if (null == body)
      body = body();
    if (null == createdAt)
      createdAt = this.timeProvider.now();

    final Note note = this.repository.save(new NoteEntity(author, body, createdAt));

    if (log.isTraceEnabled())
      log.trace("#note return : {}", note);
    return note;
  }
}
