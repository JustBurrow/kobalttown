package kr.lul.kobalttown.document.borderline;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.kobalttown.document.borderline.command.CreateNoteCmd;
import kr.lul.kobalttown.document.converter.NoteConverter;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.service.NoteService;
import kr.lul.kobalttown.document.service.params.CreateNoteParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@Service
@Transactional
class NoteBorderlineImpl implements NoteBorderline {
  protected static final Logger log = getLogger(NoteBorderlineImpl.class);

  @Autowired
  private NoteService service;
  @Autowired
  private NoteConverter converter;
  @Autowired
  private AccountService accountService;

  @Override
  public NoteDetailDto create(final CreateNoteCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#create args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account author = this.accountService.read(new ReadAccountParams(cmd, cmd.getAuthor(), cmd.getTimestamp()));
    if (null == author) {
      throw new RuntimeException("author does not exist.");
    }

    final CreateNoteParams params = new CreateNoteParams(cmd, author, cmd.getBody(), cmd.getTimestamp());
    final Note note = this.service.create(params);
    final NoteDetailDto dto = this.converter.detail(note);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }
}
