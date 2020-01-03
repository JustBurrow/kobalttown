package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountError;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import kr.lul.support.spring.web.context.ContextService;
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
  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.accountBorderline, "accountBorderline is not autowired.");
    requireNonNull(this.contextService, "contextService is not autowired.");
    requireNonNull(this.timeProvider, "timeProvider is not autowired.");
  }

  private String doCreateForm(final Model model) {
    if (!model.containsAttribute(M.CREATE_REQ)) {
      model.addAttribute(M.CREATE_REQ, new CreateAccountReq());
    }
    return V.CREATE_FORM;
  }

  private String doCreate(final CreateAccountReq req, final BindingResult result, final Model model) {
    String template;
    try {
      final CreateAccountCmd cmd = new CreateAccountCmd(this.contextService.get(), req.getNickname(),
          req.getEmail(), req.getUserKey(), req.getPassword(), this.timeProvider.now());
      final AccountDetailDto account = this.accountBorderline.create(cmd);
      if (log.isDebugEnabled())
        log.debug("#doCreate account={}", account);
      template = "redirect:/";
    } catch (final Exception e) {
      // TODO add error
      template = doCreateForm(model);
    }

    return template;
  }

  private void validate(final CreateAccountReq req, final BindingResult binding) {
    if (null != req.getPassword() && !req.getPassword().equals(req.getConfirm())) {
      binding.addError(new FieldError(M.CREATE_REQ, "confirm", null, false,
          new String[]{AccountError.CREATE_CONFIRM_NOT_MATCH}, null, "비밀번호가 일치하지 않습니다."));
    }
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.account.web.controller.AccountController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(final Model model) {
    if (log.isTraceEnabled())
      log.trace("#createForm args : model={}", model);
    notNull(model, "model");

    final String template = doCreateForm(model);

    if (log.isTraceEnabled())
      log.trace("#createForm result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String create(@ModelAttribute(M.CREATE_REQ) @Valid final CreateAccountReq req, final BindingResult binding,
      final Model model) {
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
  public String detail(@PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#detail args : id={}, model={}", id, model);
    positive(id, M.ID);
    notNull(model, "model");

    final ReadAccountCmd cmd = new ReadAccountCmd(this.contextService.get(), id, this.timeProvider.now());
    final AccountDetailDto account = this.accountBorderline.read(cmd);
    if (log.isDebugEnabled())
      log.debug("#detail account={}", account);

    model.addAttribute(M.ACCOUNT, account);

    final String template = V.DETAIL;

    if (log.isTraceEnabled())
      log.trace("#detail result : template={}, model={}", template, model);
    return template;
  }
}
