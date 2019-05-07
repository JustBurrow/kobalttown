package kr.lul.kobalttown.root.web.controller;

import kr.lul.kobalttown.article.borderline.ArticleBorderline;
import kr.lul.kobalttown.article.borderline.command.ListArticlecmd;
import kr.lul.kobalttown.article.dto.SummaryArticleDto;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.root.web.view.RootView;
import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-03-03
 */
@Controller
class RootControllerImpl implements RootController {
  private static final Logger log = getLogger(RootControllerImpl.class);

  @Autowired
  private ArticleBorderline articleBorderline;
  @Autowired
  private TimeProvider timeProvider;

  private String anonymousIndex(Model model) {
    return RootView.VIEW_INDEX_ROLE_ANONYMOUS;
  }

  private String userIndex(AccountDetails user, Pageable pageable, Model model) {
    ListArticlecmd cmd = new ListArticlecmd(this.timeProvider.now(), user, pageable.getPageNumber(),
        pageable.getPageSize());

    Page<SummaryArticleDto> page = this.articleBorderline.list(cmd);

    model.addAttribute("page", page);

    return RootView.VIEW_INDEX_ROLE_USER;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.root.web.controller.RootController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String index(AccountDetails user, @SortDefault("id") final Pageable pageable, final Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : user={}, pageable={}, model={}", user, pageable, model);
    }
    notNull(model, "model");

    final String template = null == user
        ? anonymousIndex(model)
        : userIndex(user, pageable, model);

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }

  @Override
  public String signupForm() {
    return "forward:/accounts/create";
  }

  @Override
  public String signup() {
    return "forward:/accounts";
  }
}