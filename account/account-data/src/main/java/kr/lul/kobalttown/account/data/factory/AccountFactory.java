package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public interface AccountFactory {
  /**
   * 새 계정 등록 로직용으로 계정 인스턴스를 생성.
   *
   * @param context   계정 등록 컨텍스트.
   * @param nickname  {@link Account#getNickname()}
   * @param enabled   {@link Account#isEnabled()}
   * @param createdAt {@link Account#getCreatedAt()}, {@link Account#getUpdatedAt()}
   *
   * @return 새 계정 인스턴스.
   */
  Account create(Context context, String nickname, boolean enabled, Instant createdAt);

  /**
   * Test only.
   *
   * @param id        {@link Account#getId()}
   * @param nickname  {@link Account#getNickname()}
   * @param enabled   {@link Account#isEnabled()}
   * @param createdAt {@link Account#getCreatedAt()}, {@link Account#getUpdatedAt()}
   *
   * @return 테스트용 새 계정 인스턴스.
   */
  Account create(long id, String nickname, boolean enabled, Instant createdAt);
}
