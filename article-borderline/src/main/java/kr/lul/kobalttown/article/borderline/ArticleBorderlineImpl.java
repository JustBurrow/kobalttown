package kr.lul.kobalttown.article.borderline;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.account.service.AccountService;
import kr.lul.kobalttown.article.borderline.command.CreateArticleCmd;
import kr.lul.kobalttown.article.converter.ArticleConverter;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.domain.CreateArticleException;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.article.service.ArticleService;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-25
 */
@Service
class ArticleBorderlineImpl implements ArticleBorderline {
  private static final Logger log = getLogger(ArticleBorderlineImpl.class);

  @Autowired
  private ArticleService articleService;
  @Autowired
  private ArticleConverter articleConverter;
  @Autowired
  private AccountService accountService;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.borderline.ArticleBorderline
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public DetailArticleDto create(CreateArticleCmd cmd) throws CreateArticleException {
    if (log.isTraceEnabled()) {
      log.trace("args : cmd={}", cmd);
    }
    notNull(cmd, "cmd");

    Account creator = this.accountService.read(cmd.getCreator());
    CreateArticleParams params = new CreateArticleParams(cmd.getTrackingId(), cmd.getTimestamp(),
        creator, cmd.getTitle(), cmd.getBody());
    Article article = this.articleService.create(params);
    DetailArticleDto dto = this.articleConverter.convert(article, DetailArticleDto.class);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }
}