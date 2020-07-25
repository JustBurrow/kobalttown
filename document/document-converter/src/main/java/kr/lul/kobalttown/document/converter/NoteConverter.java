package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.dto.NoteSimpleDto;
import kr.lul.kobalttown.document.dto.NoteSummaryDto;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public interface NoteConverter extends Converter<Note> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(NoteSimpleDto.class, NoteSummaryDto.class, NoteDetailDto.class);
  String PROP_COMMENTS_PAGE = "comments.page";
  String PROP_COMMENTS_SIZE = "comments.size";
  int DEFAULT_COMMENTS_PAGE = 0;
  int DEFAULT_COMMENTS_SIZE = 20;

  NoteSimpleDto simple(Note note);

  NoteSummaryDto summary(Note note);

  default NoteDetailDto detail(Note note) {
    return detail(note, ofEntries(
        entry(PROP_COMMENTS_PAGE, DEFAULT_COMMENTS_PAGE),
        entry(PROP_COMMENTS_SIZE, DEFAULT_COMMENTS_SIZE)
    ));
  }

  NoteDetailDto detail(Note note, Map<String, Object> context);

  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  @Override
  <T> T convert(Note note, Class<T> targetType);
}
