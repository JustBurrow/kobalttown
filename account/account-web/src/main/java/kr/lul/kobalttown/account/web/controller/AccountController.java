package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountMvc.C;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author justburrow
 * @since 2019/11/24
 */
@RequestMapping
public interface AccountController {
  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#CREATE_FORM}.
   */
  @GetMapping(C.CREATE_FORM)
  @PreAuthorize("isAnonymous()")
  String createForm(Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#CREATE_FORM}.
   */
  @PostMapping(C.CREATE)
  @PreAuthorize("isAnonymous()")
  String create(@ModelAttribute(M.CREATE_REQ) @Valid CreateAccountReq req, BindingResult result, Model model);

  /**
   * 인증 코드를 사용해서 계정을 활성화한다.
   *
   * @param token 인증 코드
   * @param model 모델
   *
   * @return 템플릿 이름.
   */
  @GetMapping(C.VALIDATE)
  @PreAuthorize("isAnonymous()")
  String validate(@PathVariable(M.TOKEN) String token, Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#DETAIL}.
   */
  @GetMapping(C.DETAIL)
  @PreAuthorize("isAuthenticated()")
  String detail(@PathVariable(M.ID) long id, Model model);
}
