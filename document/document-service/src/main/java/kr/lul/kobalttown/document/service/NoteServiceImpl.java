package kr.lul.kobalttown.document.service;

import kr.lul.common.data.Pagination;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.document.data.dao.NoteDao;
import kr.lul.kobalttown.document.data.factory.NoteFactory;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteUpdater;
import kr.lul.kobalttown.document.service.params.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/11
 */
@Service
class NoteServiceImpl implements NoteService {
  protected static final Logger log = getLogger(NoteServiceImpl.class);

  @Autowired
  private NoteDao dao;
  @Autowired
  private NoteFactory factory;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.service.NoteService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Note create(final CreateNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");
    positive(params.getUser().getId(), "params.author.id");

    Note note = this.factory.create(params.getContext(), params.getUser(), params.getBody(), params.getTimestamp());
    note = this.dao.create(params.getContext(), note);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", params.getContext(), note);
    return note;
  }

  @Override
  public Note read(final ReadNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);
    notNull(params, "params");

    final Note note;
    if (0L >= params.getId())
      note = null;
    else
      note = this.dao.read(params.getContext(), params.getId());

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", params.getContext(), note);
    return note;
  }

  @Override
  public Pagination<Note> list(final ListNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#limit args : params={}", params);
    notNull(params, "params");

    final Pagination<Note> notes = this.dao.list(params.getContext(), params.getPage(), params.getLimit());

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", params.getContext(), notes);
    return notes;
  }

  @Override
  public Note update(final UpdateNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#update args : params={}", params);
    notNull(params, "params");

    final Note note = this.dao.read(params.getContext(), params.getNote());
    if (!note.getAuthor().equals(params.getUser())) {
      throw new IllegalArgumentException(format("user has no update permission : user=%s, note.id=%d",
          params.getUser().toSimpleString(), params.getNote()));
    }

    final NoteUpdater updater = note.updater(params.getTimestamp());
    updater.setBody(params.getBody());

    if (log.isTraceEnabled())
      log.trace("#update (context={}) return : {}", params.getContext(), note);
    return note;
  }

  @Override
  public void delete(final DeleteNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#delete args : params={}", params);
    notNull(params, "params");
    notNull(params.getUser(), "params.user");
    positive(params.getNote(), "params.note");
    notNull(params.getTimestamp(), "params.timestamp");

    final Note note = this.dao.read(params.getContext(), params.getNote());
    if (null == note)
      throw new ValidationException("note", params.getNote(), "note does not exist : note.id=" + params.getNote());
    else
      note.delete(params.getTimestamp());

    if (log.isTraceEnabled())
      log.trace("#delete (context={}) result : note={}", params.getContext(), note);
  }
}
