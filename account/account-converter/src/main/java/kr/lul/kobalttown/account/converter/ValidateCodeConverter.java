package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.dto.ValidationCodeSummaryDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2020/01/19
 */
public interface ValidateCodeConverter extends Converter<ValidationCode> {
  public static final Set<Class> SUPPORT_TARGET_TYPES = Set.of(ValidationCodeSummaryDto.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.converter.ValidateCodeConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  @Override
  <T> T convert(ValidationCode code, Class<T> targetType);
}
