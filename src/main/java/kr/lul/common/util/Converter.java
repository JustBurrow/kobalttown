package kr.lul.common.util;

/**
 * 어떤 클래스의 오브젝트를 다른 클래스의 오브젝트로 변환하는 코드의 공통 인터페이스.
 * 공통의 인터페이스를 제공해 사용자 측에서는 분기문을 사용하지 않고 오버로딩으로 단순한 코드를 작성할 수 있도록 돕는다.
 *
 * @param <S> 원본 클래스.
 * @param <T> 타겟 클래스.
 *
 * @author justburrow
 * @since 2018. 9. 23.
 */
@FunctionalInterface
public interface Converter<S, T> {
  /**
   * 원본 오브젝트를 목표 클래스의 오브젝트로 변환한다.
   *
   * @param source 원본 오브젝트.
   *
   * @return 변환된 목표 클래스의 오브젝트.
   *
   * @throws ConvertException 변환 실패.
   */
  T convert(S source) throws ConvertException;
}
