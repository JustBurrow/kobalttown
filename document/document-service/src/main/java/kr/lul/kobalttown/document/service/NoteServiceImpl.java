package kr.lul.kobalttown.document.service;

import kr.lul.kobalttown.document.data.dao.NoteDao;
import kr.lul.kobalttown.document.data.factory.NoteFactory;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.service.params.CreateNoteParams;
import kr.lul.kobalttown.document.service.params.ReadNoteParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    positive(params.getAuthor().getId(), "params.author.id");

    Note note = this.factory.create(params.getContext(), params.getAuthor(), params.getBody(), params.getTimestamp());
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
}