package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.article.domain.Article;
import org.springframework.data.domain.Page;

/**
 * @author justburrow
 * @since 2019-04-12
 */
public interface ArticleDao {
  Article create(Article article);

  Article read(long id);

  /**
   * @param page     0-based.
   * @param pageSize positive.
   *
   * @return 최신순. not-null.
   */
  Page<Article> list(int page, int pageSize);
}