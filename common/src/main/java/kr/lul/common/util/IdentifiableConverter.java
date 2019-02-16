package kr.lul.common.util;

/**
 * 어떤 변환을 수행하는지 알 수 있는 정보를 제공하는 컨버터.
 *
 * @param <S> 원본 클래스.
 * @param <T> 타겟 클래스.
 *
 * @author justburrow
 * @since 2018. 9. 17.
 */
public interface IdentifiableConverter<S, T> extends Converter<S, T>, Identifiable<ConverterIdentifier> {
  /**
   * @return 원본 오브젝트의 클래스.
   */
  Class<S> getSourceClass();

  /**
   * @return 목표 클래스.
   */
  Class<T> getTargetClass();
}
