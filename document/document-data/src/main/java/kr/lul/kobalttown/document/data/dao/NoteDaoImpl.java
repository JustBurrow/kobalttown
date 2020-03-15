package kr.lul.kobalttown.document.data.dao;

import kr.lul.common.data.Context;
import kr.lul.common.data.Pagination;
import kr.lul.common.data.PaginationImpl;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.kobalttown.document.data.repository.NoteRepository;
import kr.lul.kobalttown.document.domain.Note;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static kr.lul.common.util.Arguments.*;
import static kr.lul.kobalttown.document.domain.Document.ATTR_ID;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.Sort.Order.desc;

/**
 * @author justburrow
 * @since 2020/02/09
 */
@Service
public class NoteDaoImpl implements NoteDao {
  protected static final Logger log = getLogger(NoteDaoImpl.class);

  @Autowired
  private NoteRepository repository;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.data.dao.NoteDao
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Note create(final Context context, final Note note) {
    if (log.isTraceEnabled())
      log.trace("#create args : context={}, note={}", context, note);
    notNull(context, "context");
    notNull(note, "note");
    notPositive(note.getId(), "note.id");
    typeOf(note, NoteEntity.class, "note");

    final Note saved = this.repository.save((NoteEntity) note);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) return : {}", context, saved);
    return saved;
  }

  @Override
  public Note read(final Context context, final long id) {
    if (log.isTraceEnabled())
      log.trace("#read args : context={}, id={}", context, id);
    notNull(context, "context");

    Note note;
    if (0L >= id)
      note = null;
    else
      note = this.repository.findById(id)
                 .orElse(null);

    if (((NoteEntity) note).isDelete())
      note = null;

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, note);
    return note;
  }

  @Override
  public Pagination<Note> list(final Context context, final int page, final int limit) {
    if (log.isTraceEnabled())
      log.trace("#list args : context={}, page={}, limit={}", context, page, limit);
    notNull(context, "context");
    notNegative(page, "page");
    positive(limit, "limit");

    final Pageable request = PageRequest.of(page, limit, Sort.by(desc(ATTR_ID)));
    final org.springframework.data.domain.Page<NoteEntity> notes = this.repository.findByDeletedAtIsNull(request);
    final Pagination<Note> list = new PaginationImpl<>(page, limit, notes.getTotalElements(),
        new ArrayList<>(notes.getContent()));

    if (log.isTraceEnabled())
      log.trace("#list (context={}) return : {}", context, list);
    return list;
  }
}
