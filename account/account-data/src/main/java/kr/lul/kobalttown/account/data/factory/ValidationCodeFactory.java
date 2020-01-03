package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.ValidationCode;

import java.time.Duration;
import java.time.Instant;

/**
 * {@link ValidationCode} 팩토리.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public interface ValidationCodeFactory {
  /**
   * 기본 유효기간의 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param code      검증 코드값.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  ValidationCode create(Context context, Account account, String code, Instant createdAt);

  /**
   * 현재 시각을 기준으로 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param code      검증 코드값.
   * @param ttl       유효기간.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  ValidationCode create(Context context, Account account, String code, Duration ttl, Instant createdAt);

  /**
   * 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param code      검증 코드값.
   * @param expireAt  만료 시각.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  ValidationCode create(Context context, Account account, String code, Instant expireAt, Instant createdAt);
}
