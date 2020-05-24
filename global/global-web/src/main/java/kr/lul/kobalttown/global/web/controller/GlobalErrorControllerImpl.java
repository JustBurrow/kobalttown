package kr.lul.kobalttown.global.web.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

import static kr.lul.support.spring.web.controller.SpringWebExceptionHandler.VIEW_400;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/05/21
 */
@Controller
class GlobalErrorControllerImpl implements GlobalErrorController {
  private static final Logger log = getLogger(GlobalErrorControllerImpl.class);

  @Override
  public String error(final HttpServletRequest request) {
    if (log.isTraceEnabled())
      log.trace("#error args : request={}", request);

    final String method = request.getMethod();
    final String url = request.getRequestURL().toString();

    if (HttpMethod.GET.name().equalsIgnoreCase(method)) {
      log.info("#error method={}, url={}", method, url);
    } else {
      log.info("#error method={}, url={}, content.type={}, content.length={}",
          method, url, request.getContentType(), request.getContentLength());
    }

    return VIEW_400;
  }
}
