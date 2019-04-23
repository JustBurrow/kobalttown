package kr.lul.kobalttown.article.service;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;

/**
 * @author justburrow
 * @since 2019-04-20
 */
public interface ArticleService {
  Article create(CreateArticleParams params);
}