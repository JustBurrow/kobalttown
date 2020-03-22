package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.dto.NoteCommentDetailDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public interface NoteCommentConverter extends Converter<NoteComment> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(NoteCommentDetailDto.class);

  NoteCommentDetailDto detail(NoteComment comment);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.common.util.Converter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  @Override
  <T> T convert(NoteComment comment, Class<T> targetType);
}
