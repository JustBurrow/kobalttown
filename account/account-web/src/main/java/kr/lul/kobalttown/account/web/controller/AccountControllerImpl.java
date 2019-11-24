package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.annotation.PostConstruct;

import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@Controller
class AccountControllerImpl implements AccountController {
  private static final Logger log = getLogger(AccountControllerImpl.class);

  @Autowired
  private AccountBorderline accountBorderline;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountBorderline, "accountBorderline is not autowired.");
  }

  @Override
  public String createForm(Model model) {
    notNull(model, "model");

    String template = V.LIST;

    if (log.isTraceEnabled())
      log.trace("#createForm result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String list(Model model) {
    if (log.isTraceEnabled())
      log.trace("#list args : model={}", model);

    String template = V.LIST;

    if (log.isTraceEnabled())
      log.trace("#list result : template={}, model={}", template, model);
    return template;
  }
}
