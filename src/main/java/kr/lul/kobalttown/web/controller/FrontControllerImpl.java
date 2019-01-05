package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.domain.GenericPaperLoader;
import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.Papermark;
import kr.lul.kobalttown.web.context.RequestContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private GenericPaperLoader genericPaperLoader;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.web.controller.FrontController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void handle(RequestContext context) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("args : context={}", context);
    }

    Papermark papermark = null;
    Paper paper = this.genericPaperLoader.load(papermark);

    context.setViewname(format(THEME_LAYOUT_FORMAT, requireNonNullElse(paper.getThemem(), Paper.DEFAULT_THEME)));
    if (log.isTraceEnabled()) {
      log.trace("result : context={}", context);
    }
  }
}