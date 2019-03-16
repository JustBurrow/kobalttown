package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.api.AccountApis.Inputs;
import kr.lul.kobalttown.account.api.CreateAccountInput;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.domain.UsedNicknameException;
import kr.lul.kobalttown.account.dto.SimpleAccountDto;
import kr.lul.kobalttown.account.web.configuration.AccountView;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

  @Autowired
  private AccountBorderline accountBorderline;

  private String doCreateForm(Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : model={}", model);
    }

    if (!model.containsAttribute(Inputs.CREATE_ATTR)) {
      model.addAttribute(Inputs.CREATE_ATTR, new CreateAccountInput());
    }

    return AccountView.VIEW_CREATE;
  }

  private String doCreate(CreateAccountInput input, BindingResult result, Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : input={}, result={}, model={}", input, result, model);
    }

    CreateAccountCmd cmd = new CreateAccountCmd(input.getNickname(), input.getPassword());
    try {
      SimpleAccountDto account = this.accountBorderline.create(cmd);

      if (log.isTraceEnabled()) {
        log.trace("result : account={}, model={}", account, model);
      }
      return "redirect:/login";
    } catch (UsedNicknameException e) {
      if (log.isInfoEnabled()) {
        log.info("fail to create account : " + input, e);
      }

      result.addError(new FieldError(Inputs.CREATE_ATTR, "nickname", input.getNickname(), false,
          new String[]{"{err.account.create.used-nickname}"}, null, "used nickname."));

      return doCreateForm(model);
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.web.controller.AccountController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : model={}", model);
    }

    String template = doCreateForm(model);

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

    final String template = result.hasErrors()
        ? doCreateForm(model)
        : doCreate(input, result, model);

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }
}