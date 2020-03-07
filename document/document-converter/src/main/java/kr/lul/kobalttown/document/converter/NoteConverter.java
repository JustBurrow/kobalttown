package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.dto.NoteSimpleDto;
import kr.lul.kobalttown.document.dto.NoteSummaryDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2020/03/07
 */
public interface NoteConverter extends Converter<Note> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(NoteSimpleDto.class, NoteSummaryDto.class, NoteDetailDto.class);

  NoteSimpleDto simple(Note note);

  NoteSummaryDto summary(Note note);

  NoteDetailDto detail(Note note);

  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  @Override
  <T> T convert(Note note, Class<T> targetType);
}
