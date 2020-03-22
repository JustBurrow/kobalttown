package kr.lul.kobalttown.document.borderline;

import kr.lul.common.data.Pagination;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.account.service.params.ReadAccountParams;
import kr.lul.kobalttown.document.borderline.command.*;
import kr.lul.kobalttown.document.converter.NoteCommentConverter;
import kr.lul.kobalttown.document.converter.NoteConverter;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.dto.NoteCommentDetailDto;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.dto.NoteSimpleDto;
import kr.lul.kobalttown.document.service.NoteService;
import kr.lul.kobalttown.document.service.params.*;
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
  private NoteCommentConverter commentConverter;
  @Autowired
  private AccountService accountService;

  @Override
  public NoteDetailDto create(final CreateNoteCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#create args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    final CreateNoteParams params = new CreateNoteParams(cmd, user, cmd.getBody(), cmd.getTimestamp());
    final Note note = this.service.create(params);
    final NoteDetailDto dto = this.converter.detail(note);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public NoteDetailDto read(final ReadNoteCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#read args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    final ReadNoteParams params = new ReadNoteParams(cmd, user, cmd.getNote(), cmd.getTimestamp());
    final Note note = this.service.read(params);
    final NoteDetailDto dto = this.converter.detail(note);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public Pagination<NoteSimpleDto> list(final ListNoteCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#list args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    final ListNoteParams params = new ListNoteParams(cmd, user, cmd.getPage(), cmd.getLimit(), cmd.getTimestamp());
    final Pagination<Note> notes = this.service.list(params);
    final Pagination<NoteSimpleDto> list = notes.map(note -> this.converter.simple(note));

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", cmd.getContext(), list);
    return list;
  }

  @Override
  public NoteDetailDto update(final UpdateNoteCmd cmd) throws ValidationException {
    if (log.isTraceEnabled())
      log.trace("#update args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    final UpdateNoteParams params = new UpdateNoteParams(cmd, user, cmd.getNote(), cmd.getBody(), cmd.getTimestamp());
    final Note note = this.service.update(params);
    final NoteDetailDto dto = this.converter.detail(note);

    if (log.isTraceEnabled())
      log.trace("#update (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }

  @Override
  public void delete(final DeleteNoteCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#delete args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    this.service.delete(new DeleteNoteParams(cmd, user, cmd.getNote(), cmd.getTimestamp()));
  }

  @Override
  public NoteCommentDetailDto comment(final CreateNoteCommentCmd cmd) {
    if (log.isTraceEnabled())
      log.trace("#comment args : cmd={}", cmd);
    notNull(cmd, "cmd");

    final Account user = this.accountService.read(new ReadAccountParams(cmd, cmd.getUser(), cmd.getTimestamp()));
    if (null == user)
      throw new ValidationException("user", cmd.getUser(), "user does not exist : " + cmd.getUser());

    final Note note = this.service.read(new ReadNoteParams(cmd, user, cmd.getNote(), cmd.getTimestamp()));

    final CreateNoteCommentParams params = new CreateNoteCommentParams(cmd, user, note, cmd.getBody(), cmd.getTimestamp());
    final NoteComment comment = this.service.comment(params);
    final NoteCommentDetailDto dto = this.commentConverter.detail(comment);

    if (log.isTraceEnabled())
      log.trace("#comment (context={}) return : {}", cmd.getContext(), dto);
    return dto;
  }
}
