package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.jpa.repository.ArticleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return null;
  }
}