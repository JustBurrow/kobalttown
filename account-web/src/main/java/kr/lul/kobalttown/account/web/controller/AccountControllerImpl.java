package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.api.AccountApis.Inputs;
import kr.lul.kobalttown.account.api.CreateAccountInput;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@Controller
class AccountControllerImpl implements AccountController {
  private static final Logger log = getLogger(AccountControllerImpl.class);

  private String doCreateForm(CreateAccountInput input, Model model) {
    if (!model.containsAttribute(Inputs.CREATE_ATTR)) {
      model.addAttribute(Inputs.CREATE_ATTR, new CreateAccountInput());
    }

    return "page/accounts/create";
  }

  private String doCreate(CreateAccountInput input, BindingResult result, Model model) {

    return "redirect:/login";
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.web.controller.AccountController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : model={}", model);
    }

    String template = doCreateForm(new CreateAccountInput(), model);

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }

  @Override
  public String create(@ModelAttribute(Inputs.CREATE_ATTR) @Valid CreateAccountInput input, BindingResult result,
      Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : input={}, result={}, model={}", input, result, model);
    }

    String template;
    if (result.hasErrors()) {
      template = doCreateForm(input, model);
    } else {
      template = doCreate(input, result, model);
    }

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }
}