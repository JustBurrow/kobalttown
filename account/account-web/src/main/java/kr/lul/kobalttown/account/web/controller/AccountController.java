package kr.lul.kobalttown.account.web.controller;

import kr.lul.common.web.http.status.exception.client.NotFound;
import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.account.web.controller.request.IssueEnableCodeReq;
import kr.lul.kobalttown.account.web.controller.request.UpdatePasswordReq;
import kr.lul.kobalttown.page.account.AccountMvc.C;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import kr.lul.support.spring.security.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
  @GetMapping(C.ENABLE)
  @PreAuthorize("isAnonymous()")
  String enable(@PathVariable(M.TOKEN) String token, Model model);

  /**
   * 유효기간 만료 등의 경우에 코드 재발급하기.
   *
   * @param model 모델
   *
   * @return 템플릿 이름.
   */
  @GetMapping(C.ISSUE_ENABLE_CODE_FORM)
  @PreAuthorize("isAnonymous()")
  String issueEnableCodeForm(Model model);

  @PostMapping(C.ISSUE_ENABLE_CODE)
  @PreAuthorize("isAnonymous()")
  String issueEnableCode(@ModelAttribute(M.ISSUE_ENABLE_CODE) @Valid IssueEnableCodeReq req, BindingResult binding, Model model);

  /**
   * {@code POST} 매핑 때문에 {@code HTTP 405 Method Not Allowed}로 응답하기 때문에 {@code 404 Not Found}가 되도록 추가.
   *
   * @return N/A
   */
  @GetMapping(C.ISSUE_ENABLE_CODE)
  default String notFoundGetValidate() {
    throw new NotFound();
  }

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#DETAIL}.
   */
  @GetMapping(C.PROFILE)
  @PreAuthorize("isAuthenticated()")
  String profile(@AuthenticationPrincipal User user, @PathVariable(M.ID) long id, Model model);

  /**
   * 유저에게 설정 페이지를 보여준다.
   *
   * @param user  현재 유저.
   * @param model 모델.
   *
   * @return 뷰 이름.
   */
  @GetMapping(C.SETTING)
  @PreAuthorize("isFullyAuthenticated()")
  String setting(@AuthenticationPrincipal User user, Model model);

  /**
   * 비밀번호 변경 폼.
   *
   * @param user  현재 유저.
   * @param model 모델.
   *
   * @return 뷰 이름.
   */
  @GetMapping(C.PASSWORD_FORM)
  @PreAuthorize("isFullyAuthenticated()")
  String passwordForm(@AuthenticationPrincipal User user, Model model);

  /**
   * 비밀번호 변경 요청 실힝.
   *
   * @param user    현재 유저.
   * @param req     변경 요청.
   * @param binding 검증 결과.
   * @param model   모델.
   *
   * @return 뷰 이름.
   */
  @PatchMapping(C.PASSWORD)
  @PreAuthorize("isFullyAuthenticated()")
  String password(@AuthenticationPrincipal User user,
      @ModelAttribute(M.UPDATE_PASSWORD_REQ) @Valid UpdatePasswordReq req, BindingResult binding,
      Model model);
}
