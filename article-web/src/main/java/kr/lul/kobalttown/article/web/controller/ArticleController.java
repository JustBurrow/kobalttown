package kr.lul.kobalttown.article.web.controller;

import kr.lul.kobalttown.article.web.ArticleApis;
import kr.lul.kobalttown.article.web.ArticleApis.ApiNames;
import kr.lul.kobalttown.article.web.ArticleApis.Inputs;
import kr.lul.kobalttown.article.web.CreateArticleInput;
import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author justburrow
 * @since 2019-05-03
 */
@PreAuthorize("isAuthenticated()")
@RequestMapping(ArticleApis.NAMESPACE)
public interface ArticleController {
  /**
   * @param user  현재 사용자.
   * @param model 모델.
   *
   * @return 아티클 작성 뷰 템플릿 이름.
   */
  @GetMapping(ApiNames.CREATE_FORM)
  String createForm(AccountDetails user, Model model);

  /**
   * @param user   현재 사용자.
   * @param input  신규 아티클 정보.
   * @param result {@code input} 검증 결과.
   * @param model  모델.
   *
   * @return 검증 오류가 없으면 리다이렉트 정보, 오류가 있을 경우엔 아티클 작성 뷰 템플릿 이름.
   */
  @PostMapping(ApiNames.CREATE)
  String create(AccountDetails user,
      @ModelAttribute(Inputs.CREATE_ATTR) @Valid CreateArticleInput input, BindingResult result,
      Model model);
}