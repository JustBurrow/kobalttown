package kr.lul.kobalttown.account.data.factory;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

import java.time.Instant;

/**
 * @author justburrow
 * @since 2019/12/07
 */
public interface CredentialFactory {
  /**
   * 새 인증정보를 만든다.
   *
   * @param context    컨텍스트.
   * @param account    인증할 계정.
   * @param publicKey  공개키.
   * @param secretHash 암호화된 비공개키.
   * @param createdAt  생성 시각.
   *
   * @return 새 인증정보 인스턴스.
   */
  Credential create(Context context, Account account, String publicKey, String secretHash, Instant createdAt);

  /**
   * 테스트용으로 새 인증정보를 만든다.
   * TEST ONLY.
   *
   * @param id         ID.
   * @param account    인증할 계정.
   * @param publicKey  공개키.
   * @param secretHash 암호화된 비공개키.
   * @param createdAt  생성 시각.
   *
   * @return 새 인증정보 인스턴스.
   */
  Credential create(long id, Account account, String publicKey, String secretHash, Instant createdAt);
}
