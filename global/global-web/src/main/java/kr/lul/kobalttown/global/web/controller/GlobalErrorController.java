package kr.lul.kobalttown.global.web.controller;

import kr.lul.kobalttown.page.global.GlobalMvc;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author justburrow
 * @since 2020/05/21
 */
@RequestMapping
public interface GlobalErrorController extends ErrorController {
  @RequestMapping(GlobalMvc.C.ERROR)
  String error(HttpServletRequest request);

  @Override
  default String getErrorPath() {
    return GlobalMvc.C.ERROR;
  }
}
