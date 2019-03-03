package kr.lul.kobalttown.root.web.controller;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Controller
class RootControllerImpl implements RootController {
  private static final Logger log = getLogger(RootControllerImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.root.web.controller.RootController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String index(Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : model={}", model);
    }

    return "page/_/index-anonymous";
  }

  @Override
  public String signupForm() {
    return "forward:/accounts/create";
  }

  @Override
  public String signup() {
    return "forward:/accounts";
  }
}