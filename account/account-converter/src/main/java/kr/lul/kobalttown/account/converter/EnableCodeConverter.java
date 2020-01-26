package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public interface EnableCodeConverter extends Converter<EnableCode> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(EnableCodeSummaryDto.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.converter.ValidateCodeConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  @Override
  <T> T convert(EnableCode code, Class<T> targetType);
}
