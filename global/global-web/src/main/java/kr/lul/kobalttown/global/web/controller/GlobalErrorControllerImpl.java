package kr.lul.kobalttown.global.web.controller;

import kr.lul.common.web.http.status.exception.client.NotFound;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static java.lang.String.format;
import static kr.lul.support.spring.web.controller.SpringWebExceptionHandler.VIEW_400;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author justburrow
 * @since 2020/05/21
 */
@Controller
class GlobalErrorControllerImpl implements GlobalErrorController {
  private static final Logger log = getLogger(GlobalErrorControllerImpl.class);

  public static final Set<Integer> NOT_FOUND_CONVERSION = Set.of(
      NOT_FOUND.value(),
      METHOD_NOT_ALLOWED.value()
  );

  @Override
  public String error(final HttpServletRequest request, final HttpServletResponse response) {
    if (log.isTraceEnabled())
      log.trace("#error args : request={}, response={}", request, response);

    final String method = request.getMethod();
    final String url = request.getRequestURL().toString();
    final int status = response.getStatus();
    if (log.isDebugEnabled())
      log.debug("#error method={}, url={}, content.type={}, content.length={}, response.status={}",
          method, url, request.getContentType(), request.getContentLength(), status);

    if (NOT_FOUND_CONVERSION.contains(status))
      throw new NotFound(format("%s %s not found", method, url));

    return VIEW_400;
  }
}
