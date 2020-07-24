package kr.lul.kobalttown.document.converter;

import kr.lul.common.data.Pagination;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;
import kr.lul.kobalttown.document.domain.Note;
import kr.lul.kobalttown.document.dto.NoteCommentDetailDto;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.dto.NoteSimpleDto;
import kr.lul.kobalttown.document.dto.NoteSummaryDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.kobalttown.document.dto.NoteDetailDto.*;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/07
 */
@Service
class NoteConverterImpl implements NoteConverter {
  protected static final Logger log = getLogger(NoteConverterImpl.class);

  @Autowired
  private NoteCommentConverter commentConverter;

  @Autowired
  private AccountConverter accountConverter;
  @Autowired
  private TimeProvider timeProvider;

  @Override
  public NoteSimpleDto simple(final Note note) {
    if (log.isTraceEnabled())
      log.trace("#simple args : note={}", note);

    final NoteSimpleDto dto;
    if (null == note)
      dto = null;
    else
      dto = new NoteSimpleDto(note.getId(), this.accountConverter.simple(note.getAuthor()), note.getBody());

    if (log.isTraceEnabled())
      log.trace("#simple return : {}", dto);
    return dto;
  }

  @Override
  public NoteSummaryDto summary(final Note note) {
    if (log.isTraceEnabled())
      log.trace("#simple args : note={}", note);

    final NoteSummaryDto dto;
    if (null == note)
      dto = null;
    else
      dto = new NoteSummaryDto(note.getId(), note.getVersion(),
          this.accountConverter.simple(note.getAuthor()),
          note.getBody(), this.timeProvider.zonedDateTime(note.getCreatedAt()));

    if (log.isTraceEnabled())
      log.trace("#simple return : {}", dto);
    return dto;
  }

  @Override
  public NoteDetailDto detail(final Note note, Map<String, Object> context) {
    if (log.isTraceEnabled())
      log.trace("#simple args : note={}", note);
    notNull(context, "context");

    final NoteDetailDto dto;
    if (null == note)
      dto = null;
    else {
      int commentsPage = context.containsKey(PROP_COMMENTS_PAGE)
                             ? (int) context.get(PROP_COMMENTS_PAGE)
                             : DEFAULT_COMMENTS_PAGE;
      int commentsSize = context.containsKey(PROP_COMMENTS_SIZE)
                             ? (int) context.get(PROP_COMMENTS_SIZE)
                             : DEFAULT_COMMENTS_SIZE;
      Pagination<NoteCommentDetailDto> comments = new Pagination<>(
          commentsPage,
          commentsSize,
          note.getComments().size(),
          this.commentConverter.detail(note.getComments()
                                           .subList(commentsPage * commentsSize, commentsPage * (commentsSize + 1)))
      );

      dto = new NoteDetailDto(note.getId(), note.getVersion(),
          this.accountConverter.convert(note.getAuthor(), AccountSimpleDto.class),
          note.getBody(),
          comments,
          this.timeProvider.zonedDateTime(note.getCreatedAt()),
          this.timeProvider.zonedDateTime(note.getUpdatedAt()));
    }

    if (log.isTraceEnabled())
      log.trace("#simple return : {}", dto);
    return dto;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.converter.NoteConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  @SuppressWarnings("unchecked")
  public <T> T convert(final Note note, final Class<T> targetType) {
    if (log.isTraceEnabled())
      log.trace("#convert args : note={}, targetType={}", note, targetType);
    notNull(targetType, "targetType");

    final T dto;
    if (null == note) {
      dto = null;
    } else if (NoteSimpleDto.class == targetType) {
      dto = (T) simple(note);
    } else if (NoteSummaryDto.class == targetType) {
      dto = (T) summary(note);
    } else if (NoteDetailDto.class == targetType) {
      dto = (T) detail(note);
    } else {
      throw new IllegalArgumentException(format("unsupported targetType : targetType=%s", targetType.getCanonicalName()));
    }

    if (log.isTraceEnabled())
      log.trace("#convert return : {}", dto);
    return dto;
  }
}
