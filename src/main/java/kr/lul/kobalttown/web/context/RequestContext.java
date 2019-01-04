package kr.lul.kobalttown.web.context;

import org.springframework.ui.ModelMap;

import java.nio.file.Path;

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
}