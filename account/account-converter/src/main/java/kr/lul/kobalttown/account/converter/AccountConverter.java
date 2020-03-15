package kr.lul.kobalttown.account.converter;

import kr.lul.common.util.Converter;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.AccountSimpleDto;

import java.util.Set;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountConverter extends Converter<Account> {
  /**
   * 사용 가능한 반환 타입.
   */
  @SuppressWarnings("rawtypes")
  Set<Class> SUPPORT_TARGET_TYPES = Set.of(AccountSimpleDto.class, AccountDetailDto.class);

  AccountSimpleDto simple(Account account);

  AccountDetailDto detail(Account account);

  /**
   * @return 변환을 지원하는 타입.
   *
   * @see #SUPPORT_TARGET_TYPES 가능한 반환 타입 목록.
   */
  @Override
  @SuppressWarnings("rawtypes")
  default Set<Class> supportTargetTypes() {
    return SUPPORT_TARGET_TYPES;
  }

  /**
   * 계정 정보 변환.
   *
   * @param account    변환할 계정.
   * @param targetType 변환할 타입. see {@link #supportTargetTypes()}
   * @param <T>        변환할 타입.
   *
   * @return 계정 정보. 계정이 {@code null}이면 {@code null}.
   *
   * @see #supportTargetTypes() 가능한 반환 타입.
   * @see #SUPPORT_TARGET_TYPES 가능한 반환 타입.
   */
  @Override
  <T> T convert(Account account, Class<T> targetType);
}
