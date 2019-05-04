package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.account.api.AccountApis.Attributes;
import kr.lul.kobalttown.account.api.CreateAccountInput;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static kr.lul.kobalttown.account.api.AccountApis.ApiNames.CREATE;
import static kr.lul.kobalttown.account.api.AccountApis.ApiNames.CREATE_FORM;
import static kr.lul.kobalttown.account.api.AccountApis.NAMESPACE;

/**
 * @author justburrow
 * @since 2019-02-28
 */
@PreAuthorize("isAuthenticated()")
@RequestMapping(NAMESPACE)
public interface AccountController {
  @PreAuthorize("isAnonymous()")
  @GetMapping({CREATE_FORM})
  String createForm(Model model);

  @PreAuthorize("isAnonymous()")
  @PostMapping(CREATE)
  String create(@ModelAttribute(Attributes.CREATE_ATTR) @Valid CreateAccountInput input, BindingResult result,
      Model model);
}