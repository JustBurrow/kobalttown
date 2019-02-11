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
   * 시스템에서 사용하기 위해 설정하는 어트리뷰트 이름. {@link java.util.Map} 타입.
   */
  String RESERVED_ATTRIBUTE_GROUP = "_";

  /**
   * {@link kr.lul.kobalttown.domain.Paper}출력에 사용할 테마.
   */
  String RESERVED_ATTRIBUTE_THEME = "theme";
  /**
   * 뷰에서 {@link kr.lul.kobalttown.domain.Paper}에 직접 접근할 수단을 제공한다. 함부로 쓰지 말 것.
   */
  @Deprecated
  String RESERVED_ATTRIBUTE_PAPER = "paper";

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