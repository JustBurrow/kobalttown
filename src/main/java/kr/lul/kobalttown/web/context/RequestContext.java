package kr.lul.kobalttown.web.context;

import kr.lul.kobalttown.domain.Paper;
import org.springframework.ui.ModelMap;

import java.nio.file.Path;
import java.util.Set;

/**
 * @author justburrow
 * @since 2018-12-25
 */
public interface RequestContext {
  /**
   * {@link Paper}를 뷰에 전달할 때 사용하는 이름. 되도록 쓰지 말 것.
   */
  String ATTR_PAPER = "paper";
  /**
   * 예약된 어트리뷰트 이름.
   */
  Set<String> RESERVED_ATTRIBUTE_NAMES = Set.of(ATTR_PAPER);
  /**
   * 테마의 레이아웃 뷰 이름의 포맷.
   *
   * @see String#format(String, Object...)
   */
  String THEME_LAYOUT_FORMAT = "layout/%s/index";

  /**
   * @return
   */
  Verb getVerb();

  /**
   * @return URL path.
   */
  Path getPath();

  /**
   * @return 리퀘스트 모델.
   */
  ModelMap getModel();

  /**
   * @param view 뷰 이름.
   */
  void setViewname(String view);

  /**
   * @return 뷰 이름.
   */
  String getViewname();

  void setPaper(Paper paper);
}