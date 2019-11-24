package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountDetailDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountConverter extends Converter<Account> {
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(AccountDetailDto.class);

  @Override
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }
}
