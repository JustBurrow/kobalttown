package kr.lul.kobalttown.test.article;

import kr.lul.kobalttown.article.dao.ArticleDao;
import kr.lul.kobalttown.article.domain.Article;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author justburrow
 * @since 2019-04-19
 */
public class ArticleDaoTestUtil extends ArticleJpaTestUtil {
  @Autowired
  private ArticleDao articleDao;

  public Article createdArticle() {
    return this.articleDao.create(prePersistArticle());
  }
}