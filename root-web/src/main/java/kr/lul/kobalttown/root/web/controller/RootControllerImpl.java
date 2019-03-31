package kr.lul.kobalttown.root.web.controller;

import kr.lul.kobalttown.root.web.configuration.RootView;
import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
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
  public String index(AccountDetails user, Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : user={}, model={}", user, model);
    }
    notNull(model, "model");

    final String template = null == user
        ? RootView.VIEW_INDEX_ROLE_ANONYMOUS
        : RootView.VIEW_INDEX_ROLE_USER;

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
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