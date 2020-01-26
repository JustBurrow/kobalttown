package kr.lul.kobalttown.account.data.dao;

import kr.lul.common.data.Context;
import kr.lul.kobalttown.account.domain.EnableCode;

import java.util.List;

/**
 * @author justburrow
 * @since 2020/01/03
 */
public interface EnableCodeDao {
  /**
   * 신규 발행한 계정 화성화 코드를 저장한다.
   *
   * @param context 컨텍스트.
   * @param code    신규 발행한 계정 활성화 코드.
   *
   * @return 저장된 계정 활성화 코드.
   */
  EnableCode create(Context context, EnableCode code);

  /**
   * 코드(토큰)에 해당하는 검증 코드를 반환한다.
   *
   * @param context 컨텍스트
   * @param token   검증용 코드(토큰).
   *
   * @return 검증 코드. 없으면 {@code null}. 사용 및 만료 여부는 무관.
   */
  EnableCode read(Context context, String token);

  /**
   * 활성화 코드가 존재하는지 확인한다.
   *
   * @param context 컨텍스트.
   * @param token   활성화 코드(토큰).
   *
   * @return 발행된 코드이면 {@code true}. 사용 및 만료 여부는 무관.
   */
  boolean exists(Context context, String token);

  /**
   * 발송한 메일을 기준으로 계정 활성화 코드를 찾는다.
   *
   * @param context 컨텍스트.
   * @param email   발송 메일 주소.
   *
   * @return 활성화 코드. 사용 및 만료 여부는 무관. {@code not-null}.
   */
  List<EnableCode> list(Context context, String email);
}
