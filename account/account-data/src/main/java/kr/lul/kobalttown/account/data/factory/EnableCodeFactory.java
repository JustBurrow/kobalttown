package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.domain.EnableCode.Status;

import java.time.Duration;
import java.time.Instant;

/**
 * {@link EnableCode} 팩토리.
 *
 * @author justburrow
 * @since 2020/01/03
 */
public interface EnableCodeFactory {
  /**
   * 기본 유효기간의 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param email     E-Mail.
   * @param token     검증 코드값.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  EnableCode create(Context context, Account account, String email, String token, Instant createdAt);

  /**
   * 현재 시각을 기준으로 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param email     E-Mail.
   * @param token     검증 코드값.
   * @param ttl       유효기간.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  EnableCode create(Context context, Account account, String email, String token, Duration ttl, Instant createdAt);

  /**
   * 검증 코드를 생성.
   *
   * @param context   컨텍스트.
   * @param account   검증할 계정.
   * @param email     E-Mail.
   * @param token     검증 코드값.
   * @param expireAt  만료 시각.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  EnableCode create(Context context, Account account, String email, String token, Instant expireAt, Instant createdAt);

  /**
   * 테스트용 검증 코드를 생성.
   * TEST ONLY.
   *
   * @param id        ID.
   * @param account   계정.
   * @param email     E-Mail.
   * @param token     코드.
   * @param expireAt  만료 시각.
   * @param status    상태
   * @param statusAt  상태 변경 시각.
   * @param createdAt 생성 시각.
   *
   * @return 검증 코드.
   */
  EnableCode create(long id, Account account, String email, String token, Instant expireAt, Status status,
      Instant statusAt, Instant createdAt);
}
