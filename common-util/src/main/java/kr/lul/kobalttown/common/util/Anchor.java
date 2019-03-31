package kr.lul.kobalttown.common.util;

/**
 * Java 코드에서 패키지에 접근할 수 있는 수단을 제공해 패키지 정보가 필요한 코드가 하드코딩한 문자열이 아닌 클래스 참조를 사용할 수 있도록 돕는다.
 *
 * 예)
 * <ul>
 * <li>스프링의 어노테이션 기반 설정에서 컴포넌트를 스캔할 클래스 지정하기.</li>
 * <li>특정 인터페이스를 구현하는 클래스를 테스트해야 할 때, 클래스를 찾을 패키지 지정하기.</li>
 * </ul>
 *
 * @author justburrow
 * @see Package
 * @see Package#getName()
 * @since 2018. 9. 17.
 */
public interface Anchor {
}