package kr.lul.kobalttown.document.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.document.domain.NoteComment;
import kr.lul.kobalttown.document.dto.NoteCommentDetailDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.in;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/22
 */
@Service
class NoteCommentConverterImpl implements NoteCommentConverter {
  protected static final Logger log = getLogger(NoteCommentConverterImpl.class);

  @Autowired
  private AccountConverter accountConverter;
  @Autowired
  private TimeProvider timeProvider;

  @Override
  public NoteCommentDetailDto detail(final NoteComment comment) {
    if (log.isTraceEnabled())
      log.trace("#detail args : comment={}", comment);

    final NoteCommentDetailDto dto;
    if (null == comment) {
      dto = null;
    } else {
      dto = new NoteCommentDetailDto(comment.getId(), this.accountConverter.simple(comment.getAuthor()), comment.getBody(),
          this.timeProvider.zonedDateTime(comment.getCreatedAt()));
    }

    if (log.isTraceEnabled())
      log.trace("#detail return : {}", dto);
    return dto;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T convert(final NoteComment comment, final Class<T> targetType) {
    if (log.isTraceEnabled())
      log.trace("#convert args : comment={}, targetType={}", comment, targetType);
    notNull(targetType, "targetType");
    in(targetType, SUPPORT_TARGET_TYPES, "targetType");

    final T dto;
    if (NoteCommentDetailDto.class == targetType) {
      dto = (T) detail(comment);
    } else {
      throw new IllegalArgumentException(format("unsupported target type : %s", targetType));
    }

    if (log.isTraceEnabled())
      log.trace("#convert return : {}", dto);
    return dto;
  }
}
