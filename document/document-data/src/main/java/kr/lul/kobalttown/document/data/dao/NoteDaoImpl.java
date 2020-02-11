package kr.lul.kobalttown.document.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.document.data.entity.NoteEntity;
import kr.lul.kobalttown.document.data.repository.NoteRepository;
import kr.lul.kobalttown.document.domain.Note;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static kr.lul.common.util.Arguments.*;
import static org.slf4j.LoggerFactory.getLogger;

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

    final Note note;
    if (0L >= id)
      note = null;
    else
      note = this.repository.findById(id)
                 .orElse(null);

    if (log.isTraceEnabled())
      log.trace("#read (context={}) return : {}", context, note);
    return note;
  }
}
