package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static kr.lul.common.util.Arguments.in;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/19
 */
@Service
class EnableCodeConverterImpl implements EnableCodeConverter {
  protected static final Logger log = getLogger(EnableCodeConverterImpl.class);

  @Autowired
  private TimeProvider timeProvider;

  EnableCodeSummaryDto summary(final EnableCode code) {
    if (log.isTraceEnabled())
      log.trace("#summary args : code={}", code);

    final EnableCodeSummaryDto dto;
    if (null == code)
      dto = null;
    else
      dto = new EnableCodeSummaryDto(code.getId(), code.getEmail(), code.getToken(),
          this.timeProvider.zonedDateTime(code.getExpireAt()));

    if (log.isTraceEnabled())
      log.trace("#summary return : {}", code);
    return dto;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.converter.EnableCodeConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  @SuppressWarnings("unchecked")
  public <T> T convert(final EnableCode code, final Class<T> targetType) {
    if (log.isTraceEnabled())
      log.trace("#convert args : code={}, targetType={}", code, targetType);
    in(targetType, SUPPORT_TARGET_TYPES, "targetType");

    final T dto;
    if (EnableCodeSummaryDto.class == targetType) {
      dto = (T) summary(code);
    } else {
      throw new IllegalArgumentException("unsupported target type : " + targetType.getCanonicalName());
    }

    if (log.isTraceEnabled())
      log.trace("#convert return : {}", dto);
    return dto;
  }
}
