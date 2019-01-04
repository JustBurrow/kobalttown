package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.web.context.RequestContext;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;

import static java.lang.String.format;
import static java.util.Objects.requireNonNullElse;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2018-12-25
 */
@Controller
class FrontControllerImpl implements FrontController {
  private static final Logger log = getLogger(FrontControllerImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.web.controller.FrontController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void control(RequestContext context) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("args : context={}", context);
    }

    String theme = null;

    context.setViewname(format("layout/%s/layout", requireNonNullElse(theme, "basic")));
    if (log.isTraceEnabled()) {
      log.trace("result : context={}", context);
    }
  }
}