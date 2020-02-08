package kr.lul.kobalttown.account.test;

import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

import static kr.lul.kobalttown.account.domain.AccountUtil.nickname;
import static kr.lul.kobalttown.account.domain.CredentialUtil.email;
import static kr.lul.kobalttown.account.domain.CredentialUtil.userKey;

/**
 * 테스트용 {@link Account} 데이터 생성 유틸리티.
 *
 * @author justburrow
 * @since 2020/02/08
 */
public interface AccountTestTool {
  String DEFAULT_PASSWORD = "password";

  /**
   * 임의의 정보를 가진 계정을 생성한다.
   * <ul>
   *   <li>임의의 값 : 닉네임, 이메일, 유저키</li>
   *   <li>고정값 : 비밀번호</li>
   *   <li>기타 : 생성시각(현재시각)</li>
   * </ul>
   *
   * @return 임의의 신규 계정.
   */
  default Account account() {
    return account(nickname(), true, email(), userKey(), DEFAULT_PASSWORD, null);
  }

  /**
   * 신규 계정을 생성한다.
   *
   * @param nickname  {@code null}, {@code ''}이면 임의의 값을 사용.
   * @param enabled   계정 활성화 여부.
   * @param email     {@code null}, {@code ''}이면 임의의 값을 사용.
   * @param userKey   {@code null}, {@code ''}이면 임의의 값을 사용.
   * @param password  {@code null}, {@code ''}이면 {@link #DEFAULT_PASSWORD}를 사용.
   * @param createdAt {@code null}이면 현재 시각을 사용.
   *
   * @return 신규 계정.
   */
  Account account(String nickname, boolean enabled, String email, String userKey, String password, Instant createdAt);
}
