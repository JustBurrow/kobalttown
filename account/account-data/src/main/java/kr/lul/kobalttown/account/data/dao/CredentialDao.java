package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Credential;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface CredentialDao {
  Credential create(Context uuidContext, Credential credential);

  /**
   * @param context   유저뫄 무관한 내부 로직 실행일 경우엔 {@code null}.
   * @param publicKey {@link Credential#getPublicKey()}
   *
   * @return 인증정보 혹은 {@code null}.
   */
  Credential read(Context context, String publicKey);
}
