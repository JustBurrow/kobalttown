package kr.lul.kobalttown.global.web.controller;

import kr.lul.kobalttown.page.root.RootMvc.C;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author justburrow
 * @since 2020/05/21
 */
@RequestMapping
public interface RootController extends ErrorController {
  @RequestMapping(C.ERROR)
  String error(HttpServletRequest request);
}
