package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import kr.lul.kobalttown.article.jpa.repository.ArticleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static kr.lul.kobalttown.article.jpa.mapping.ArticleMapping.E;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static kr.lul.kobalttown.common.util.Arguments.notPositive;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@Service
class ArticleDaoImpl implements ArticleDao {
  private static final Logger log = getLogger(ArticleDaoImpl.class);

  @Autowired
  private ArticleRepository articleRepository;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.dao.ArticleDao
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Article create(Article article) {
    if (log.isTraceEnabled()) {
      log.trace("args : article={}", article);
    }
    notNull(article, "article");
    notPositive(article.getId(), "article.id");

    ArticleEntity result = this.articleRepository.save((ArticleEntity) article);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", result);
    }
    return result;
  }

  @Override
  public Article read(long id) {
    if (log.isTraceEnabled()) {
      log.trace("args : id={}", id);
    }

    Article article = this.articleRepository.getOne(id);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", article);
    }
    return article;
  }

  @Override
  public Page<Article> list(int page, int pageSize) {
    if (log.isTraceEnabled()) {
      log.trace("args : page={}, pageSize={}", page, pageSize);
    }

    Pageable pageable = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, E.ATTR_ID));
    Page<Article> list = this.articleRepository.findAll(pageable)
        .map(article -> article);

    if (log.isTraceEnabled()) {
      log.trace("return : {}", list);
    }
    return list;
  }
}