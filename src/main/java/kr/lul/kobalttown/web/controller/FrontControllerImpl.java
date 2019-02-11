package kr.lul.kobalttown.web.controller;

import kr.lul.kobalttown.domain.Paper;
import kr.lul.kobalttown.domain.PaperNotFoundException;
import kr.lul.kobalttown.domain.Papermark;
import kr.lul.kobalttown.loader.PaperLoaderDelegator;
import kr.lul.kobalttown.web.context.PapermarkConverter;
import kr.lul.kobalttown.web.context.RequestContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2018-12-25
 */
@Controller
class FrontControllerImpl implements FrontController {
  private static final Logger log = getLogger(FrontControllerImpl.class);

  @Autowired
  private PapermarkConverter papermarkConverter;
  @Autowired
  private PaperLoaderDelegator paperLoaderDelegator;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.web.controller.FrontController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void handle(RequestContext context) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("args : context={}", context);
    }

    Papermark papermark = this.papermarkConverter.convert(context);
    try {
      Paper paper = this.paperLoaderDelegator.load(papermark);
      context.setPaper(paper);
    } catch (PaperNotFoundException e) {
      if (log.isInfoEnabled()) {
        log.info(e.getMessage(), e);
      }
      throw e;
    }

    if (log.isTraceEnabled()) {
      log.trace("result : context={}", context);
    }
  }
}