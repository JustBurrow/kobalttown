package kr.lul.kobalttown.account.converter;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;
import kr.lul.kobalttown.account.dto.SummaryAccountDto;
import kr.lul.kobalttown.common.util.Converter;

import java.util.Set;

/**
 * @author justburrow
 * @since 2019-02-28
 */
public interface AccountConverter extends Converter<Account> {
  Set<Class> SUPPORT_TYPES = Set.of(SimpleAccountDto.class, SummaryAccountDto.class);

  SimpleAccountDto simple(Account account);

  SummaryAccountDto summary(Account account);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.util.Converter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Set<Class> supportsTarget() {
    return SUPPORT_TYPES;
  }
}