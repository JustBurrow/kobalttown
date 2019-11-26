package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.web.controller.request.CreateAccountReq;
import kr.lul.kobalttown.page.account.AccountMvc.C;
import kr.lul.kobalttown.page.account.AccountMvc.M;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static kr.lul.global.security.authority.Role.ROLE_ANONYMOUS;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountController {
  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#CREATE_FORM}.
   */
  @GetMapping(C.CREATE_FORM)
//  @PreAuthorize("hasRole('" + ROLE_ANONYMOUS + "')")
  String createForm(Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#CREATE_FORM}.
   */
  @PostMapping(C.CREATE)
  @PreAuthorize("hasRole('" + ROLE_ANONYMOUS + "')")
  String create(@ModelAttribute(M.CREATE_REQ) @Valid CreateAccountReq req, BindingResult result, Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#LIST}.
   */
  @GetMapping(C.LIST)
  String list(Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#DETAIL}.
   */
  @GetMapping(C.DETAIL)
  String detail(@PathVariable(M.ID) long id, Model model);

  /**
   * See {@link kr.lul.kobalttown.page.account.AccountPage#ACTIVATE}.
   */
  @GetMapping(C.ACTIVATE)
  @PreAuthorize("hasRole('" + ROLE_ANONYMOUS + "')")
  String activate(@PathVariable(M.TOKEN) String token, Model model);
}
