package kr.lul.kobalttown.root.web.controller;

import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@PreAuthorize("permitAll()")
@RequestMapping
public interface RootController {
  @GetMapping
  String index(AccountDetails user, Model model);

  @PreAuthorize("isAnonymous()")
  @GetMapping("/signup")
  String signupForm();

  @PreAuthorize("isAnonymous()")
  @PostMapping("/signup")
  String signup();
}