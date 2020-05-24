package kr.lul.kobalttown.document.service;

import kr.lul.common.data.Pagination;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.document.data.dao.NoteDao;
import kr.lul.kobalttown.document.data.factory.NoteCommentFactory;
import kr.lul.kobalttown.document.data.factory.NoteFactory;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.domain.NoteUpdater;
import kr.lul.kobalttown.document.service.params.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
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
  @Autowired
  private NoteCommentFactory commentFactory;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.service.NoteService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Note create(final CreateNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#create args : params={}", params);
    notNull(params, "params");

    Note note = this.factory.create(params, params.getUser(), params.getBody(), params.getTimestamp());
    note = this.dao.create(params, note);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", params.getId(), note);
    return note;
  }

  @Override
  public Note read(final ReadNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#read args : params={}", params);
    notNull(params, "params");

    final Note note;
    if (0L >= params.getNote())
      note = null;
    else
      note = this.dao.read(params, params.getNote());

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", params.getId(), note);
    return note;
  }

  @Override
  public Pagination<Note> list(final ListNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#limit args : params={}", params);
    notNull(params, "params");

    final Pagination<Note> notes = this.dao.list(params, params.getPage(), params.getLimit());

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", params.getId(), notes);
    return notes;
  }

  @Override
  public Note update(final UpdateNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#update args : params={}", params);
    notNull(params, "params");

    final Note note = this.dao.read(params, params.getNote());
    if (!note.getAuthor().equals(params.getUser())) {
      throw new IllegalArgumentException(format("user has no update permission : user=%s, note.id=%d",
          params.getUser().toSimpleString(), params.getNote()));
    }

    final NoteUpdater updater = note.updater(params.getTimestamp());
    updater.setBody(params.getBody());

    if (log.isTraceEnabled())
      log.trace("#update (context={}) return : {}", params.getId(), note);
    return note;
  }

  @Override
  public void delete(final DeleteNoteParams params) {
    if (log.isTraceEnabled())
      log.trace("#delete args : params={}", params);
    notNull(params, "params");

    final Note note = this.dao.read(params, params.getNote());
    if (null == note)
      throw new ValidationException("note", params.getNote(), "note does not exist : note.id=" + params.getNote());
    else
      note.delete(params.getTimestamp());

    if (log.isTraceEnabled())
      log.trace("#delete (context={}) result : note={}", params.getId(), note);
  }

  @Override
  public NoteComment comment(final CreateNoteCommentParams params) {
    if (log.isTraceEnabled())
      log.trace("#comment args : params={}", params);
    notNull(params, "params");

    NoteComment comment = this.commentFactory.create(params, params.getUser(), params.getNote(), params.getBody(),
        params.getTimestamp());
    comment = this.dao.create(params, comment);

    if (log.isTraceEnabled())
      log.trace("#comment (context={}) return : {}", params.getId(), comment);
    return comment;
  }

  @Override
  public void delete(final DeleteNoteCommentParams params) {
    if (log.isTraceEnabled())
      log.trace("#delete args : params={}", params);
    notNull(params, "params");
    notNull(params.getUser(), "params.user");

    final Note note = this.dao.read(params, params.getNote());
    if (null == note)
      throw new ValidationException("note", params.getNote(), "note does not exist : note=" + params.getNote());

    try {
      note.deleteComment(params.getUser(), params.getComment());
    } catch (final NoSuchElementException e) {
      throw new ValidationException("comment", params.getComment(), e);
    } catch (final ValidationException e) {
      log.warn("#delete fail to delete note comment : params=" + params, e);
      if ("account".equals(e.getTargetName()))
        throw new ValidationException("user", params.getUser(), "no delete comment permission : comment=" + params.getComment());
      else
        throw e;
    }

    if (log.isTraceEnabled())
      log.trace("#delete (context={}) result : note={}", params.getId(), note);
  }
}
