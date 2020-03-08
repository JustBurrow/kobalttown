package kr.lul.kobalttown.document.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.kobalttown.document.borderline.NoteBorderline;
import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.kobalttown.page.note.NoteMvc.V;
import kr.lul.support.spring.security.userdetails.User;
import kr.lul.support.spring.web.context.ContextService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
}
