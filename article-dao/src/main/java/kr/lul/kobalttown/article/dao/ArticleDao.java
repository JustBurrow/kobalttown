package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.domain.Article;

/**
 * @author justburrow
 * @since 2019-04-12
 */
public interface ArticleDao {
  Article create(Article article);

  Article read(long id);
}