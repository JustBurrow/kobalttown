package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.web.context.RequestContext;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author justburrow
 * @since 2018-12-25
 */
@RequestMapping
public interface FrontController {
  /**
   * 테마의 레이아웃 뷰 이름의 포맷.
   *
   * @see String#format(String, Object...)
   */
  String THEME_LAYOUT_FORMAT = "layout/%s/index";

  /**
   * 리퀘스트 처리.
   *
   * @param context 리퀘스트 정보.
   *
   * @return 템플릿 이름.
   *
   * @throws Exception
   */
  @RequestMapping("/**")
  void handle(RequestContext context) throws Exception;
}