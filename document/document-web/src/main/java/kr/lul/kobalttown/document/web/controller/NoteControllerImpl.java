package kr.lul.kobalttown.document.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.document.borderline.NoteBorderline;
import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.kobalttown.page.note.NoteMvc.V;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import static java.lang.String.format;
import static kr.lul.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@Controller
class NoteControllerImpl implements NoteController {
  protected static final Logger log = getLogger(NoteControllerImpl.class);

  @Autowired
  private NoteBorderline borderline;
  @Autowired
  private ContextService contextService;
  @Autowired
  private TimeProvider timeProvider;

  private String doCreateForm(final Context context, final User user, final CreateNoteReq req, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doCreateForm args : context={}, user={}, req={}, model={}", context, user, req, model);
    notNull(context, "context");
    notNull(user, "user");
    notNull(model, "model");

    if (null == req) {
      model.addAttribute(M.CREATE_REQ, new CreateNoteReq());
    }

    return V.CREATE;
  }

  private String doCreate(final Context context, final User user, final CreateNoteReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doCreate args : context={}, user={}, req={}, binding={}, model={}", context, user, req, binding, model);

    String template;
    try {
      template = format("redirect:%s/%d", NoteMvc.C.GROUP, 0);
    } catch (final ValidationException e) {
      template = doCreateForm(context, user, req, model);
    }

    if (log.isTraceEnabled())
      log.trace("#doCreate (context={}) return : {}", context, template);
    return template;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.document.web.controller.NoteController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(@AuthenticationPrincipal final User user, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#createForm args : user={}, model={}", user, model);
    notNull(user, "user");
    notNull(model, "model");

    final Context context = this.contextService.get();
    final String template = doCreateForm(context, user, null, model);

    if (log.isTraceEnabled())
      log.trace("#createForm (context={}) result : template={}, template={}", context, template, template);
    return template;
  }

  @Override
  public String create(@AuthenticationPrincipal final User user,
      @ModelAttribute(M.CREATE_REQ) final CreateNoteReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#create args : user={}, req={}, binding={}, model={}", user, req, binding, model);
    notNull(user, "user");
    notNull(req, "req");
    notNull(binding, "binding");
    notNull(model, "model");

    final Context context = this.contextService.get();
    final String template;
    if (binding.hasErrors())
      template = doCreateForm(context, user, req, model);
    else
      template = doCreate(context, user, req, binding, model);

    if (log.isTraceEnabled())
      log.trace("#create (context={}) result : template={}, model", context, template, model);
    return template;
  }
}
