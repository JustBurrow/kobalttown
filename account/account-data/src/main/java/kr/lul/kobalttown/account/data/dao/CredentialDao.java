package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.domain.Credential;

import java.util.List;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface CredentialDao {
  /**
   * 신규 인증정보 저장.
   *
   * @param context    컨텍스트.
   * @param credential 신규 인증정보.
   *
   * @return 새로 저장된 인증정보.
   */
  Credential create(Context context, Credential credential);

  /**
   * @param context   유저뫄 무관한 내부 로직 실행일 경우엔 {@code null}.
   * @param publicKey {@link Credential#getPublicKey()}
   *
   * @return 인증정보 혹은 {@code null}.
   */
  Credential read(Context context, String publicKey);

  /**
   * 계정의 인증정보를 읽는다.
   *
   * @param context 컨텍스트. nullable.
   * @param account 인증할 계정.
   *
   * @return {@code account}를 인증할 수 있는 인증정보.
   */
  List<Credential> read(Context context, Account account);

  void delete(Context context, Credential credential);

  /**
   * 사용중이 공개키인지 확인한다.
   *
   * @param context   컨텍스트.
   * @param publicKey 공개키.
   *
   * @return 사용중이면 {@code true}.
   */
  boolean existsPublicKey(Context context, String publicKey);
}
