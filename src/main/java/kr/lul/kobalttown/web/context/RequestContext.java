package kr.lul.kobalttown.web.context;

import org.springframework.ui.ModelMap;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author justburrow
 * @since 2018-12-25
 */
public interface RequestContext {
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

  /**
   * 뷰모델에 어트리뷰트를 추가한다.
   *
   * @param attributes 추가할 어트리뷰트.
   */
  void addModelAttributes(Map<String, ?> attributes);

  /**
   * 뷰모델에 어트리뷰트를 추가한다.
   *
   * @param name      어트리뷰트 이름.
   * @param attribute 어트리뷰트.
   */
  void addModelAttributes(String name, Object attribute);
}