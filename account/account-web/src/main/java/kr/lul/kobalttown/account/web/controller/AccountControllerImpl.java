package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.DisabledPropertyException;
import kr.lul.common.util.TimeProvider;
import kr.lul.common.util.ValidationException;
import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.borderline.command.CreateAccountCmd;
import kr.lul.kobalttown.account.borderline.command.IssueValidateCmd;
import kr.lul.kobalttown.account.borderline.command.ReadAccountCmd;
import kr.lul.kobalttown.account.borderline.command.ValidateAccountCmd;
import kr.lul.kobalttown.account.domain.ValidationCode;
import kr.lul.kobalttown.account.domain.ValidationCodeStatusException;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.ValidationCodeSummaryDto;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.account.web.controller.request.IssueValidateReq;
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
  private AccountBorderline borderline;
  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  @PostConstruct
  private void postConstruct() {
    requireNonNull(this.borderline, "accountBorderline is not autowired.");
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
      final AccountDetailDto account = this.borderline.create(cmd);
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

  private String doValidate(final Context context, final String token, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doValidate args : context={}, token={}, model={}", context, token, model);

    final ValidateAccountCmd cmd = new ValidateAccountCmd(context, token, this.timeProvider.now());
    if (log.isDebugEnabled())
      log.debug("#doValidate (context={}) cmd={}", context, cmd);

    String template;
    try {
      final AccountDetailDto account = this.borderline.validate(cmd);
      if (log.isDebugEnabled())
        log.debug("#doValidate (context={}) account={}", context, account);

      model.addAttribute(M.ACCOUNT, account);
      model.addAttribute(M.VALIDATED_AT, cmd.getTimestamp());

      template = V.VALIDATE_SUCCESS;
    } catch (final DisabledPropertyException e) {
      log.warn(format("#doValidate (context=%s) e=%s", context, e), e);
      throw new NotFound(e);
    } catch (final ValidationCodeStatusException e) {
      log.warn(format("#doValidate (context=%s) e=%s", context, e), e);
      template = V.VALIDATE_FAIL;
    }

    if (log.isTraceEnabled())
      log.trace("#doValidate (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  private String doIssueValidateCodeForm(final Context context,
      final IssueValidateReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doIssueValidateCodeForm args : context={}, req={}, binding={}, model={}", context, req, binding, model);

    if (null == req) {
      model.addAttribute(M.ISSUE_VALIDATE_REQ, new IssueValidateReq());
    } else {
      req.setEmail(null);
    }

    if (log.isTraceEnabled())
      log.trace("#doIssueValidateCodeForm (context={}) result : template={}, model={}", context, V.VALIDATE_ISSUE, model);
    return V.VALIDATE_ISSUE;
  }

  private String doIssueValidateCode(final Context context, final IssueValidateReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doIssueValidateCode args : context={}, req={}, binding={}, model={}", context, req, binding, model);

    final IssueValidateCmd cmd = new IssueValidateCmd(context, req.getEmail(), this.timeProvider.now());
    log.info("#issue (context={}) cmd={}", context, cmd);
    String template;
    try {
      final ValidationCodeSummaryDto dto = this.borderline.issue(cmd);
      model.addAttribute(M.VALIDATE_CODE, dto);
      template = V.VALIDATE_ISSUED;
    } catch (final ValidationException e) {
      log.warn(format("#doIssueValidateCode (context=%s) cmd=%s, e=%s", context, cmd, e), e);

      binding.addError(new FieldError(M.ISSUE_VALIDATE_REQ, e.getTargetName(), e.getTarget(), false,
          new String[]{AccountError.ISSUE_VALIDATE_CODE_FAIL}, null, "Fail to issue account validate code."));

      template = doIssueValidateCodeForm(context, req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#doIssueValidateCode (context={}) result : template={}, model={}", context, template, model);
    return template;
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
  public String validate(@PathVariable(M.TOKEN) final String token, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#validate args : token={}, model={}", token, model);

    final Context context = this.contextService.get();
    final String template;

    try {
      ValidationCode.CODE_VALIDATOR.validate(token);
      template = doValidate(context, token, model);
    } catch (final ValidationException e) {
      log.warn("#validate (context={}) e=" + e, context, e);
      throw new NotFound(e);
    }

    if (log.isTraceEnabled())
      log.trace("#validate (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String issueValidateCode(final Model model) {
    if (log.isTraceEnabled())
      log.trace("#issueValidateCode args : model={}", model);

    final Context context = this.contextService.get();
    final String template = doIssueValidateCodeForm(context, null, null, model);

    if (log.isTraceEnabled())
      log.trace("#issueValidateCode (context={}) result : model={}", context, model);
    return template;
  }

  @Override
  public String issue(@ModelAttribute(M.ISSUE_VALIDATE_REQ) @Valid final IssueValidateReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#issue args : req={}, binding={}, model={}", req, binding, model);

    final Context context = this.contextService.get();
    final String template;
    if (binding.hasErrors()) {
      template = doIssueValidateCodeForm(context, req, binding, model);
    } else {
      template = doIssueValidateCode(context, req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#issue (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String detail(@PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#detail args : id={}, model={}", id, model);
    positive(id, M.ID);
    notNull(model, "model");

    final ReadAccountCmd cmd = new ReadAccountCmd(this.contextService.get(), id, this.timeProvider.now());
    final AccountDetailDto account = this.borderline.read(cmd);
    if (log.isDebugEnabled())
      log.debug("#detail account={}", account);

    model.addAttribute(M.ACCOUNT, account);

    final String template = V.DETAIL;

    if (log.isTraceEnabled())
      log.trace("#detail result : template={}, model={}", template, model);
    return template;
  }
}
