package kr.lul.kobalttown.article.converter;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.article.dto.SimpleArticleDto;
import kr.lul.kobalttown.article.dto.SummaryArticleDto;
import kr.lul.kobalttown.common.util.Converter;

import java.util.Set;

/**
 * @author justburrow
 * @since 2019-04-24
 */
public interface ArticleConverter extends Converter<Article> {
  Set<Class> SUPPORT_TYPES = Set.of(SimpleArticleDto.class, SummaryArticleDto.class, DetailArticleDto.class);

  SimpleArticleDto simple(Article article);

  SummaryArticleDto summary(Article article);

  DetailArticleDto detail(Article article);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.common.util.Converter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  default Set<Class> supportsTarget() {
    return SUPPORT_TYPES;
  }
}