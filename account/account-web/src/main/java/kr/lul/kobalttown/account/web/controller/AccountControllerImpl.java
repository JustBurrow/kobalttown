package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.DisabledPropertyException;
import kr.lul.common.util.TimeProvider;
import kr.lul.common.util.ValidationException;
import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.account.borderline.AccountBorderline;
import kr.lul.kobalttown.account.borderline.command.*;
import kr.lul.kobalttown.account.domain.EnableCode;
import kr.lul.kobalttown.account.domain.EnableCodeStatusException;
import kr.lul.kobalttown.account.dto.AccountDetailDto;
import kr.lul.kobalttown.account.dto.EnableCodeSummaryDto;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.account.web.controller.request.IssueEnableCodeReq;
import kr.lul.kobalttown.account.web.controller.request.UpdatePasswordReq;
import kr.lul.kobalttown.page.account.AccountError;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.kobalttown.page.account.AccountMvc.V;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.Instant;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static kr.lul.common.util.Arguments.notNull;
import static kr.lul.common.util.Arguments.positive;
import static kr.lul.kobalttown.page.account.AccountError.UPDATE_CONFIRM_NOT_MATCH;
import static kr.lul.kobalttown.page.account.AccountError.UPDATE_PASSWORD_NOT_UPDATED;
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

  private void validate(final CreateAccountReq req, final BindingResult binding) {
    if (null != req.getPassword() && !req.getPassword().equals(req.getConfirm())) {
      binding.addError(new FieldError(M.CREATE_REQ, "confirm", null, false,
          new String[]{AccountError.CREATE_CONFIRM_NOT_MATCH}, null, "비밀번호가 일치하지 않습니다."));
    }
  }

  private String doCreate(final CreateAccountCmd cmd, final BindingResult result, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doCreate args : cmd={}, result={}, model={}", cmd, result, model);

    final Context context = this.contextService.get();
    String template;
    try {
      final AccountDetailDto account = this.borderline.create(cmd);
      if (log.isDebugEnabled())
        log.debug("#doCreate account={}", account);
      template = "redirect:/";
    } catch (final Exception e) {
      // TODO add error
      template = doCreateForm(model);
    }

    if (log.isTraceEnabled())
      log.trace("#doCreate (context={}) result : template={}, result={}, model={}", context, template, result, model);
    return template;
  }

  private String doEnable(final EnableAccountCmd cmd, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doEnable args :cmd={}, model={}", cmd, model);

    if (log.isDebugEnabled())
      log.debug("#doEnable (context={}) cmd={}", cmd.getContext(), cmd);

    String template;
    try {
      final AccountDetailDto account = this.borderline.enable(cmd);
      if (log.isDebugEnabled())
        log.debug("#doEnable (context={}) account={}", cmd.getContext(), account);

      model.addAttribute(M.ACCOUNT, account);
      model.addAttribute(M.ENABLED_AT, cmd.getTimestamp());

      template = V.ENABLE_SUCCESS;
    } catch (final DisabledPropertyException e) {
      log.warn(format("#doEnable (context=%s) e=%s", cmd.getContext(), e), e);
      throw new NotFound(e);
    } catch (final EnableCodeStatusException e) {
      log.warn(format("#doEnable (context=%s) e=%s", cmd.getContext(), e), e);
      template = V.ENABLE_FAIL;
    }

    if (log.isTraceEnabled())
      log.trace("#doEnable (context={}) result : template={}, model={}", cmd.getContext(), template, model);
    return template;
  }

  private String doIssueEnableCodeForm(final Context context,
      final IssueEnableCodeReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doIssueEnableCodeForm args : context={}, req={}, binding={}, model={}", context, req, binding, model);

    if (null == req) {
      model.addAttribute(M.ISSUE_ENABLE_CODE, new IssueEnableCodeReq());
    } else {
      req.setEmail(null);
    }

    if (log.isTraceEnabled())
      log.trace("#doIssueEnableCodeForm (context={}) result : template={}, model={}", context, V.ISSUE_ENABLE_CODE, model);
    return V.ISSUE_ENABLE_CODE;
  }

  private String doIssueEnableCode(final Context context, final IssueEnableCodeReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doIssueEnableCode args : context={}, req={}, binding={}, model={}", context, req, binding, model);

    final IssueEnableCodeCmd cmd = new IssueEnableCodeCmd(context, req.getEmail(), this.timeProvider.now());
    log.info("#doIssueEnableCode (context={}) cmd={}", context, cmd);
    String template;
    try {
      final EnableCodeSummaryDto dto = this.borderline.issue(cmd);
      model.addAttribute(M.ENABLE_CODE, dto);
      template = V.ENABLE_CODE_ISSUED;
    } catch (final ValidationException e) {
      log.warn(format("#doIssueEnableCode (context=%s) cmd=%s, e=%s", context, cmd, e), e);

      binding.addError(new FieldError(M.ISSUE_ENABLE_CODE, e.getTargetName(), e.getTarget(), false,
          new String[]{AccountError.ISSUE_VALIDATE_CODE_FAIL}, null, "Fail to issue account enable code."));

      template = doIssueEnableCodeForm(context, req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#doIssueEnableCode (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  private String doPasswordForm(final Context context, final User user, final UpdatePasswordReq req, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doPasswordForm args : context={}, user={}, req={}, model={}", context, user, req, model);

    final ReadAccountCmd cmd = new ReadAccountCmd(context, user.getId(), this.timeProvider.now());
    final AccountDetailDto account = this.borderline.read(cmd);
    log.info("#doPasswordForm (context={}) account={}", context, account);

    model.addAttribute(M.ACCOUNT, account);
    if (null == req) {
      model.addAttribute(M.UPDATE_PASSWORD_REQ, new UpdatePasswordReq());
    }

    return V.PASSWORD;
  }

  private void validate(final UpdatePasswordReq req, final BindingResult binding) {
    notNull(req, "req");
    notNull(binding, "binding");

    if (null != req.getPassword()) {
      if (!req.getPassword().equals(req.getConfirm())) {
        binding.addError(new FieldError(M.UPDATE_PASSWORD_REQ, "confirm", null, false,
            new String[]{UPDATE_CONFIRM_NOT_MATCH}, null, "비밀번호가 일치하지 않습니다."));
      } else if (req.getPassword().equals(req.getCurrent())) {
        binding.addError(new FieldError(M.UPDATE_PASSWORD_REQ, "password", null, false,
            new String[]{UPDATE_PASSWORD_NOT_UPDATED}, null, "비밀번호가 병경되지 않았습니다."));
      }
    }
  }

  private String doPassword(final Context context, final User user, final UpdatePasswordReq req, final BindingResult binding,
      final Model model) {
    notNull(context, "context");
    notNull(req, "req");
    notNull(binding, "binding");
    notNull(model, "model");

    final UpdatePasswordCmd cmd = new UpdatePasswordCmd(context, user.getId(), req.getCurrent(), req.getPassword(),
        this.timeProvider.now());
    String template;
    try {
      this.borderline.update(cmd);
      template = V.PASSWORD_UPDATED;
    } catch (final ValidationException e) {
      binding.addError(new FieldError(M.UPDATE_PASSWORD_REQ, e.getTargetName(), null, false, new String[]{}, null, "UNKNOWN"));
      template = doPasswordForm(cmd.getContext(), user, req, model);
    }

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
      final CreateAccountCmd cmd = new CreateAccountCmd(this.contextService.get(), req.getNickname(),
          req.getEmail(), req.getUserKey(), req.getPassword(), this.timeProvider.now());
      template = doCreate(cmd, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#create result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String enable(@PathVariable(M.TOKEN) final String token, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#enable args : token={}, model={}", token, model);

    final Context context = this.contextService.get();
    final String template;

    try {
      EnableCode.TOKEN_VALIDATOR.validate(token);
      template = doEnable(new EnableAccountCmd(context, token, this.timeProvider.now()), model);
    } catch (final ValidationException e) {
      log.warn("#enable (context={}) e=" + e, context, e);
      throw new NotFound(e);
    }

    if (log.isTraceEnabled())
      log.trace("#enable (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String issueEnableCodeForm(final Model model) {
    if (log.isTraceEnabled())
      log.trace("#issueEnableCode args : model={}", model);

    final Context context = this.contextService.get();
    final String template = doIssueEnableCodeForm(context, null, null, model);

    if (log.isTraceEnabled())
      log.trace("#issueEnableCode (context={}) result : model={}", context, model);
    return template;
  }

  @Override
  public String issueEnableCode(@ModelAttribute(M.ISSUE_ENABLE_CODE) @Valid final IssueEnableCodeReq req,
      final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#issueEnableCode args : req={}, binding={}, model={}", req, binding, model);

    final Context context = this.contextService.get();
    final String template;
    if (binding.hasErrors()) {
      template = doIssueEnableCodeForm(context, req, binding, model);
    } else {
      template = doIssueEnableCode(context, req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#issueEnableCode (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String profile(@AuthenticationPrincipal final User user, @PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#profile args : user={}, id={}, model={}", user, id, model);
    notNull(user, "user");
    positive(user.getId(), "user.id");
    positive(id, M.ID);
    notNull(model, "model");

    final ReadAccountCmd cmd = new ReadAccountCmd(this.contextService.get(), id, this.timeProvider.now());
    final AccountDetailDto account = this.borderline.read(cmd);
    if (log.isDebugEnabled())
      log.debug("#profile account={}", account);

    model.addAttribute(M.ACCOUNT, account);

    final String template = V.DETAIL;

    if (log.isTraceEnabled())
      log.trace("#profile result : template={}, model={}", template, model);
    return template;
  }

  @Override
  public String setting(@AuthenticationPrincipal final User user, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#setting args : user={}, model={}", user, model);
    notNull(user, "user");
    positive(user.getId(), "user.id");
    notNull(model, "model");

    final Context context = this.contextService.get();

    final String template = V.SETTING;
    if (log.isTraceEnabled())
      log.trace("#setting (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String passwordForm(@AuthenticationPrincipal final User user, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#passwordForm args : user={}, model={}", user, model);
    notNull(user, "user");
    positive(user.getId(), "user.id");
    notNull(model, "model");

    final Context context = this.contextService.get();
    final String template = doPasswordForm(context, user, null, model);

    if (log.isTraceEnabled())
      log.trace("#passwordForm (context={}) result : template={}, model={}", context, template, model);
    return template;
  }

  @Override
  public String password(@AuthenticationPrincipal final User user,
      @ModelAttribute(M.UPDATE_PASSWORD_REQ) @Valid final UpdatePasswordReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#password args : user={}, req={}, binding={}, model={}", user, req, binding, model);
    notNull(user, "user");
    positive(user.getId(), "user.id");
    notNull(req, "req");
    notNull(binding, "binding");
    notNull(model, "model");

    if (!binding.hasErrors())
      validate(req, binding);

    final Context context = this.contextService.get();
    final Instant timestamp = this.timeProvider.now();

    final String template;
    if (binding.hasErrors()) {
      if (log.isDebugEnabled())
        log.debug("#password password does not match.");
      template = doPasswordForm(context, user, req, model);
    } else {
      if (log.isDebugEnabled())
        log.debug("#password request has no binding error.");

      template = doPassword(context, user, req, binding, model);
    }

    if (log.isTraceEnabled())
      log.trace("#password (context={}) result : template={}, model={}", context, template, model);
    return template;
  }
}
