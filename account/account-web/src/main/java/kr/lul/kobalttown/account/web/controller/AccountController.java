package kr.lul.kobalttown.account.web.controller;

import kr.lul.kobalttown.page.account.AccountMvc.C;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author justburrow
 * @since 2019/11/24
 */
public interface AccountController {
  @GetMapping(C.CREATE_FORM)
  String createForm(Model model);

  @GetMapping(C.LIST)
  String list(Model model);
}
