package kr.lul.kobalttown.domain;

import java.nio.file.Path;
import java.util.Map;

/**
 * 코발트 타운으로 서비스하는 웹 문서의 단위.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface Paper {
  /**
   * {@link Paper}가 제강하는 모델 어트리큐트의 마킹 인터페이스.
   *
   * TODO 마킹 인터페이스로 괜찮은가? 페이퍼의 속성을 리스트와 맵 모두로 제고항 방법을 갖춰야 하지 않을까? 속성 사이를 순환할 가능성은 없는가?
   */
  interface Attribute {
  }

  /**
   * 일련번호를 사용하는 ID.
   *
   * @return 페이퍼 ID.
   */
  int getId();

  /**
   * 페이퍼 경로. 유일값이기 때문에 ID로 사용 가능함.
   *
   * @return 페이퍼 경로.
   */
  Path getPath();

  /**
   * @return 테마 이름.
   */
  String getTheme();

  /**
   * @return 어트리뷰트 맵.
   */
  Map<String, Attribute> getAttributeMap();
}