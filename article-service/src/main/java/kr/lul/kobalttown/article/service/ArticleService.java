package kr.lul.kobalttown.article.service;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.domain.CreateArticleException;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;
import kr.lul.kobalttown.article.service.params.ListArticleParams;
import org.springframework.data.domain.Page;

/**
 * @author justburrow
 * @since 2019-04-20
 */
public interface ArticleService {
  Article create(CreateArticleParams params) throws CreateArticleException;

  Article read(long id);

  Page<Article> list(ListArticleParams params);
}