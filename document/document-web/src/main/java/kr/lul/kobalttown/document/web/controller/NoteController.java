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
  /**
   * 새 노트 작성 페이지.
   *
   * @param user  현재 유저.
   * @param model 모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @GetMapping(C.CREATE_FORM)
  @PreAuthorize("hasAnyRole('USER')")
  String createForm(@AuthenticationPrincipal User user, Model model);

  /**
   * 새 노트 작성.
   *
   * @param user    현재 유저.
   * @param req     새 노트 정보.
   * @param binding 바인딩 결과.
   * @param model   모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @PostMapping(C.CREATE)
  @PreAuthorize("hasAnyRole('USER')")
  String create(@AuthenticationPrincipal User user,
      @ModelAttribute(M.CREATE_REQ) CreateNoteReq req, BindingResult binding,
      Model model);

  /**
   * 노트 상세 정보 페이지.
   *
   * @param user  현재 유저.
   * @param id    노트 ID.
   * @param model 모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @GetMapping(C.DETAIL)
  @PreAuthorize("hasAnyRole('USER')")
  String detail(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);

  /**
   * 노트 편집 페이지.
   *
   * @param user  현재 유저.
   * @param id    노트 ID.
   * @param model 모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @GetMapping(C.UPDATE_FORM)
  @PreAuthorize("hasAnyRole('USER')")
  String updateForm(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);

  /**
   * 노트 정보 수정.
   *
   * @param user    현재 유저.
   * @param id      노트 ID.
   * @param req     노트의 새 정보.
   * @param binding 바인딩 결과.
   * @param model   모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @PatchMapping(C.UPDATE)
  @PreAuthorize("hasAnyRole('USER')")
  String update(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id,
      @ModelAttribute(M.UPDATE_REQ) @Valid UpdateNoteReq req, BindingResult binding,
      Model model);

  /**
   * 노트 삭제하기.
   *
   * @param user  현재 유저.
   * @param id    노트 ID.
   * @param model 모델.
   *
   * @return 뷰 템플릿 이름.
   */
  @DeleteMapping(C.DELETE)
  @PreAuthorize("hasAnyRole('USER')")
  String delete(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);
}
