package kr.lul.kobalttown.article.service;

import kr.lul.kobalttown.article.dao.ArticleDao;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.domain.CreateArticleException;
import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@Service
class ArticleServiceImpl implements ArticleService {
  private static final Logger log = getLogger(ArticleServiceImpl.class);

  @Autowired
  private ArticleDao articleDao;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.service.ArticleService
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Article create(CreateArticleParams params) throws CreateArticleException {
    if (log.isTraceEnabled()) {
      log.trace("args : params={}", params);
    }
    notNull(params, "params");

    ArticleEntity article = new ArticleEntity(params.getTitle(),
        params.getBody(),
        params.getCreator(),
        params.getTimestampMillis());
    Article created = this.articleDao.create(article);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", created);
    }
    return article;
  }
}