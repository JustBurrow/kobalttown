package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountError;
import kr.lul.kobalttown.page.account.AccountMvc.C;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;
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

  private String doCreateForm(Model model) {
    if (!model.containsAttribute(M.CREATE_REQ)) {
      model.addAttribute(M.CREATE_REQ, new CreateAccountReq());
    }
    return V.CREATE_FORM;
  }

  private String doCreate(CreateAccountReq req, BindingResult result, Model model) {
    String template;
    try {
      // TODO create account
      long id = 0L;
      template = format("redirect:%s/%d", C.GROUP, id);
    } catch (Exception e) {
      // TODO add error
      template = doCreateForm(model);
    }

    return template;
  }

  private void validate(CreateAccountReq req, BindingResult binding) {
    if (null != req.getPassword() && !req.getPassword().equals(req.getConfirm())) {
      binding.addError(new FieldError(M.CREATE_REQ, "confirm", null, false,
          new String[]{AccountError.CREATE_CONFIRM_NOT_MATCH}, null, "비밀번호가 일치하지 않습니다."));
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.web.controller.AccountController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(Model model) {
    if (log.isTraceEnabled())
      log.trace("#createForm args : model={}", model);
    notNull(model, "model");

    String template = doCreateForm(model);

    if (log.isTraceEnabled())
      log.trace("#createForm result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String create(@ModelAttribute(M.CREATE_REQ) @Valid CreateAccountReq req, BindingResult binding, Model model) {
    if (log.isTraceEnabled())
      log.trace("#create args : req={}, binding={}, model={}", req, binding, model);
    notNull(req, M.CREATE_REQ);
    notNull(binding, "binding");
    notNull(model, "model");

    validate(req, binding);

    final String template;
    if (binding.hasErrors()) {
      template = doCreateForm(model);
    } else {
      template = doCreate(req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#create result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String list(Model model) {
    if (log.isTraceEnabled())
      log.trace("#list args : model={}", model);
    notNull(model, "model");

    String template = V.LIST;

    if (log.isTraceEnabled())
      log.trace("#list result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String detail(@PathVariable(M.ID) long id, Model model) {
    if (log.isTraceEnabled())
      log.trace("#detail args : id={}, model={}", id, model);
    positive(id, M.ID);
    notNull(model, "model");

    // TODO account loading

    String template = V.DETAIL;

    if (log.isTraceEnabled())
      log.trace("#detail result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String activate(@PathVariable(M.TOKEN) String token, Model model) {
    if (log.isTraceEnabled())
      log.trace("#activate args : token={}, model={}", token, model);

    String template = V.ACTIVATE;

    if (log.isTraceEnabled())
      log.trace("#activate result : template={}, model={}", template, model);
    return template;
  }
}
