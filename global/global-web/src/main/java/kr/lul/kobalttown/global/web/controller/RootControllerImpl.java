package kr.lul.kobalttown.global.web.controller;

import kr.lul.kobalttown.page.root.RootMvc.C;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import static kr.lul.support.spring.web.controller.SpringWebExceptionHandler.VIEW_400;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/05/21
 */
@Controller
class RootControllerImpl implements RootController {
  private static final Logger log = getLogger(RootControllerImpl.class);

  @Override
  public String error(final HttpServletRequest request) {
    if (log.isTraceEnabled())
      log.trace("#error args : request={}", request);

    final String method = request.getMethod();
    final String url = request.getRequestURL().toString();

    log.info("#error method={}, url={}", method, url);

    return VIEW_400;
  }

  @Override
  public String getErrorPath() {
    return C.ERROR;
  }
}
