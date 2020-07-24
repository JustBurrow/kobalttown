package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.dto.NoteCommentDetailDto;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static kr.lul.common.util.Arguments.notNull;

/**
 * @author justburrow
 * @since 2020/03/22
 */
public interface NoteCommentConverter extends Converter<NoteComment> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(NoteCommentDetailDto.class);

  NoteCommentDetailDto detail(NoteComment comment);

  default List<NoteCommentDetailDto> detail(List<NoteComment> comments) {
    notNull(comments, "comments");
    return comments.stream()
               .map(this::detail)
               .collect(toList());
  }

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
