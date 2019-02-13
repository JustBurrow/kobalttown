package kr.lul.kobalttown.domain;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 코발트 타운으로 서비스하는 웹 문서의 단위.
 * 한번의 리퀘스트에 응답하는 데이터({@code org.springframework.ui.ModelMap}에 해당)와 메타 데이터를 포함한다.
 *
 * @author justburrow
 * @since 2019-01-05
 */
public interface Paper {
  /**
   * 폴란드어로 종이조각이라는 뜻.
   * 웹 페이지 렌더링의 일정 영역을 의미한다.
   *
   * @see java.util.Map.Entry 맵 엔트리 역할.
   */
  interface Kartka<V> {
    /**
     * 영역의 이름.
     *
     * @return 이름.
     */
    String getName();

    /**
     * 영역을 그릴 때 사용할 데이터.
     *
     * @return 값.
     */
    V getValue();
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

  List<Kartka> getKartkas();

  Map<String, Object> getAttributes();
}