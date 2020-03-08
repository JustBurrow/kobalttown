package kr.lul.kobalttown.document.web.controller;

import kr.lul.kobalttown.document.web.controller.request.CreateNoteReq;
import kr.lul.kobalttown.page.note.NoteMvc.C;
import kr.lul.kobalttown.page.note.NoteMvc.M;
import kr.lul.support.spring.security.userdetails.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author justburrow
 * @since 2020/03/08
 */
@RequestMapping
public interface NoteController {
  @GetMapping(C.CREATE_FORM)
  String createForm(@AuthenticationPrincipal User user, Model model);

  @PostMapping(C.CREATE)
  String create(@AuthenticationPrincipal User user,
      @ModelAttribute(M.CREATE_REQ) CreateNoteReq req, BindingResult binding,
      Model model);
}
