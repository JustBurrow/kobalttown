package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.dto.ValidationCodeSummaryDto;
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
class ValidateCodeConverterImpl implements ValidateCodeConverter {
  protected static final Logger log = getLogger(ValidateCodeConverterImpl.class);

  @Autowired
  private TimeProvider timeProvider;

  private ValidationCodeSummaryDto summary(final ValidationCode code) {
    if (null == code) {
      return null;
    }

    final ValidationCodeSummaryDto dto = new ValidationCodeSummaryDto(
        code.getId(), code.getEmail(), this.timeProvider.zonedDateTime(code.getExpireAt()));

    return dto;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.converter.ValidateCodeConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  @SuppressWarnings("unchecked")
  public <T> T convert(final ValidationCode code, final Class<T> targetType) {
    if (log.isTraceEnabled())
      log.trace("#convert args : code={}, targetType={}", code, targetType);
    in(targetType, SUPPORT_TARGET_TYPES, "targetType");

    final T dto;
    if (ValidationCodeSummaryDto.class == targetType) {
      dto = (T) summary(code);
    } else {
      throw new IllegalArgumentException("unsupported target type : " + targetType.getCanonicalName());
    }

    if (log.isTraceEnabled())
      log.trace("#convert return : {}", dto);
    return dto;
  }
}
