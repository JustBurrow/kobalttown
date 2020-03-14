package kr.lul.kobalttown.document.web.controller;

import kr.lul.common.data.Context;
import kr.lul.common.util.TimeProvider;
import kr.lul.common.util.ValidationException;
import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.document.borderline.NoteBorderline;
import kr.lul.kobalttown.document.borderline.command.CreateNoteCmd;
import kr.lul.kobalttown.document.borderline.command.DeleteNoteCmd;
import kr.lul.kobalttown.document.borderline.command.ReadNoteCmd;
import kr.lul.kobalttown.document.borderline.command.UpdateNoteCmd;
import kr.lul.kobalttown.document.dto.NoteDetailDto;
import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.document.web.controller.request.UpdateNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc.C;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

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
      final CreateNoteCmd cmd = new CreateNoteCmd(context, user.getId(), req.getBody(), this.timeProvider.now());
      final NoteDetailDto note = this.borderline.create(cmd);
      template = format("redirect:%s/%d", C.GROUP, note.getId());
    } catch (final ValidationException e) {
      template = doCreateForm(context, user, req, model);
    }

    if (log.isTraceEnabled())
      log.trace("#doCreate (context={}) return : {}", context, template);
    return template;
  }

  /**
   * @param context 컨텍스트.
   * @param user    세션 유저.
   * @param id      노트 ID.
   * @param req     리퀘스트. nullable.
   * @param model   모델.
   *
   * @return 템플릿.
   */
  private String doUpdateForm(final Context context, final User user, final long id, final UpdateNoteReq req, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doUpdateForm args : context={}, user={}, id={}, req={}, model={}", context, user, id, req, model);

    final ReadNoteCmd cmd = new ReadNoteCmd(context, user.getId(), id, this.timeProvider.now());
    final NoteDetailDto note = this.borderline.read(cmd);
    if (null == note)
      throw new NotFound("note does not exist : note.id=" + id);

    model.addAttribute(M.NOTE, note);

    if (null == req)
      model.addAttribute(M.UPDATE_REQ, new UpdateNoteReq(note.getBody()));

    if (log.isTraceEnabled())
      log.trace("#doUpdateForm (context={}) result : model={}", context, model);
    return V.UPDATE;
  }

  private String doUpdate(final Context context, final User user, final long id,
      final UpdateNoteReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#doUpdate args : context={}, user={}, id={}, req={}, binding={}, model={}",
          context, user, id, req, binding, model);

    final UpdateNoteCmd cmd = new UpdateNoteCmd(context, user.getId(), id, req.getBody(), this.timeProvider.now());
    String template;
    try {
      final NoteDetailDto note = this.borderline.update(cmd);
      template = format("redirect:%s/%d", C.GROUP, note.getId());
    } catch (final ValidationException e) {
      log.warn(format("fail to update note : user.id=%d, note.id=%d, req=%s", user.getId(), id, req), e);
      binding.addError(new FieldError(M.UPDATE_REQ, e.getTargetName(), e.getTarget(),
          false, null, null, "fail to update note : id=" + id));
      template = doUpdateForm(context, user, id, req, model);
    }

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
      log.trace("#createForm (context={}) result : template='{}', template={}", context, template, template);
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
      log.trace("#create (context={}) result : template='{}', model={}", context, template, model);
    return template;
  }

  @Override
  public String detail(@AuthenticationPrincipal final User user, @PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#detail args : user={}, id={}, model={}", user, id, model);

    if (0L >= id)
      throw new NotFound("note.id=" + id);

    final Context context = this.contextService.get();
    final ReadNoteCmd cmd = new ReadNoteCmd(context, user.getId(), id, this.timeProvider.now());
    final NoteDetailDto note = this.borderline.read(cmd);

    final String template;
    if (null != note) {
      model.addAttribute(M.NOTE, note);
      template = V.DETAIL;
    } else {
      throw new NotFound("note does not exist : id=" + id);
    }

    if (log.isTraceEnabled())
      log.trace("#detail (context={}) result : template='{}', model={}", context, template, model);
    return template;
  }

  @Override
  public String updateForm(@AuthenticationPrincipal final User user, @PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#updateForm args : user={}, id={}, model={}", user, id, model);
    notNull(user, "user");
    notNull(model, "model");

    if (0L >= id)
      throw new NotFound("note.id=" + id);

    final Context context = this.contextService.get();
    final String template = doUpdateForm(context, user, id, null, model);

    if (log.isTraceEnabled())
      log.trace("#updateForm (context={}) result : template='{}', model={}", context, template, model);
    return template;
  }

  @Override
  public String update(@AuthenticationPrincipal final User user, @PathVariable(M.ID) final long id,
      @ModelAttribute(M.UPDATE_REQ) @Valid final UpdateNoteReq req, final BindingResult binding,
      final Model model) {
    if (log.isTraceEnabled())
      log.trace("#update args : user={}, id={}, req={}, binding={}, model={}", user, id, req, binding, model);
    notNull(user, "user");
    notNull(req, "req");
    notNull(binding, "binding");
    notNull(model, "model");

    if (0L >= id)
      throw new NotFound("note.id=" + id);

    final Context context = this.contextService.get();
    final String template = doUpdate(context, user, id, req, binding, model);

    if (log.isTraceEnabled())
      log.trace("#update (context={}) result : template='{}', model={}", context, template, model);
    return template;
  }

  @Override
  public String delete(@AuthenticationPrincipal final User user, @PathVariable(M.ID) final long id, final Model model) {
    if (log.isTraceEnabled())
      log.trace("#delete args : user={}, id={}, model={}", user, id, model);
    notNull(user, "user");
    notNull(model, "model");
    if (0L >= id)
      throw new NotFound("note does not exist : note.id=" + id);

    final Context context = this.contextService.get();
    final DeleteNoteCmd cmd = new DeleteNoteCmd(context, user.getId(), id, this.timeProvider.now());
    this.borderline.delete(cmd);

    final String template = format("redirect:%s", C.LIST);
    if (log.isTraceEnabled())
      log.trace("#delete (context={}) result : template='{}', model={}", context, template, model);
    return template;
  }
}
