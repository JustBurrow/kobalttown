package kr.lul.kobalttown.document.web.controller;

import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.document.web.controller.request.UpdateNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc.C;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.support.spring.security.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@RequestMapping
public interface NoteController {
  @GetMapping(C.CREATE_FORM)
  @PreAuthorize("hasAnyRole('USER')")
  String createForm(@AuthenticationPrincipal User user, Model model);

  @PostMapping(C.CREATE)
  @PreAuthorize("hasAnyRole('USER')")
  String create(@AuthenticationPrincipal User user,
      @ModelAttribute(M.CREATE_REQ) CreateNoteReq req, BindingResult binding,
      Model model);

  @GetMapping(C.DETAIL)
  @PreAuthorize("hasAnyRole('USER')")
  String detail(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);

  @GetMapping(C.UPDATE_FORM)
  @PreAuthorize("hasAnyRole('USER')")
  String updateForm(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);

  @PatchMapping(C.UPDATE)
  @PreAuthorize("hasAnyRole('USER')")
  String update(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id,
      @ModelAttribute(M.UPDATE_REQ) @Valid UpdateNoteReq req, BindingResult binding,
      Model model);
}
