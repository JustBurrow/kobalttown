package kr.lul.kobalttown.article.web.controller;

import kr.lul.kobalttown.article.borderline.ArticleBorderline;
import kr.lul.kobalttown.article.borderline.command.CreateArticleCmd;
import kr.lul.kobalttown.article.domain.CreateArticleException;
import kr.lul.kobalttown.article.domain.CreateArticleFieldException;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.article.web.ArticleApis;
import kr.lul.kobalttown.article.web.ArticleApis.Inputs;
import kr.lul.kobalttown.article.web.CreateArticleInput;
import kr.lul.kobalttown.article.web.view.ArticleView;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.support.spring.security.AccountDetails;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.UUID;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-05-03
 */
@Controller
class ArticleControllerImpl implements ArticleController {
  private static final Logger log = getLogger(ArticleControllerImpl.class);

  @Autowired
  private ArticleBorderline articleBorderline;
  @Autowired
  private TimeProvider timeProvider;

  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  private String doCreateForm(final Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : model={}", model);
    }

    if (!model.containsAttribute(Inputs.CREATE_ATTR)) {
      model.addAttribute(Inputs.CREATE_ATTR, new CreateArticleInput());
    }

    String template = ArticleView.CREATE_FORM;

    if (log.isTraceEnabled()) {
      log.trace("return : {}", template);
    }
    return template;
  }

  private String doCreate(final AccountDetails user,
      final CreateArticleInput input, final BindingResult result,
      final Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : user={}, input={}, result={}, model={}", user, input, result, model);
    }

    CreateArticleCmd cmd = new CreateArticleCmd(UUID.randomUUID(), this.timeProvider.now(),
        user.getId(), input.getTitle(), input.getBody());
    String template;
    try {
      DetailArticleDto article = this.articleBorderline.create(cmd);
      template = format("redirect:%s/%d", ArticleApis.NAMESPACE, article.getId());
    } catch (CreateArticleFieldException e) {
      result.addError(new FieldError(Inputs.CREATE_ATTR, e.getField(), e.getMessage()));
      template = doCreateForm(model);
    } catch (CreateArticleException e) {
      result.addError(new ObjectError(Inputs.CREATE_ATTR, e.getMessage()));
      template = doCreateForm(model);
    }

    if (log.isTraceEnabled()) {
      log.trace("return : {}", template);
    }
    return template;
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.web.controller.ArticleController
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public String createForm(final AccountDetails user, final Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : user={}, model={}", user, model);
    }
    notNull(user, "user");
    notNull(model, "model");

    String template = doCreateForm(model);

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }

  @Override
  public String create(final AccountDetails user,
      @ModelAttribute(Inputs.CREATE_ATTR) @Valid final CreateArticleInput input, final BindingResult result,
      final Model model) {
    if (log.isTraceEnabled()) {
      log.trace("args : user={}, input={}, result={}, model={}", user, input, result, model);
    }
    notNull(user, "user");
    notNull(input, "input");
    notNull(result, "result");
    notNull(model, "model");

    final String template = result.hasErrors()
        ? doCreateForm(model)
        : doCreate(user, input, result, model);

    if (log.isTraceEnabled()) {
      log.trace("result : template={}, model={}", template, model);
    }
    return template;
  }
}