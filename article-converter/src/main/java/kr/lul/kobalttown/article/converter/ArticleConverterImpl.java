package kr.lul.kobalttown.article.converter;

import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.article.dto.SimpleArticleDto;
import kr.lul.kobalttown.article.dto.SummaryArticleDto;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static kr.lul.kobalttown.common.util.Arguments.in;
import static kr.lul.kobalttown.common.util.Arguments.notNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-24
 */
@Service
class ArticleConverterImpl implements ArticleConverter {
  private static final Logger log = getLogger(ArticleConverterImpl.class);

  @Autowired
  private TimeProvider timeProvider;

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.article.converter.ArticleConverter
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public SimpleArticleDto simple(Article article) {
    if (log.isTraceEnabled()) {
      log.trace("args : article={}", article);
    }

    SimpleArticleDto dto = null == article
        ? null
        : new SimpleArticleDto(article.getId(), article.getTitle());

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }

  @Override
  public SummaryArticleDto summary(Article article) {
    if (log.isTraceEnabled()) {
      log.trace("args : article={}", article);
    }

    SummaryArticleDto dto = null == article
        ? null
        : new SummaryArticleDto(article.getId(), article.getTitle(), article.getSummary(),
        this.timeProvider.zonedDateTime(article.getCreatedAt()));

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }

  @Override
  public DetailArticleDto detail(Article article) {
    if (log.isTraceEnabled()) {
      log.trace("args : article={}", article);
    }

    DetailArticleDto dto = null == article
        ? null
        : new DetailArticleDto(article.getId(), article.getTitle(),
        article.getSummary(), article.getBody(),
        this.timeProvider.zonedDateTime(article.getCreatedAt()));

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }

  @Override
  public <T> T convert(Article article, Class<T> targetType) {
    if (log.isTraceEnabled()) {
      log.trace("args : article={}, targetType={}", article, targetType);
    }
    notNull(targetType, "targetType");
    in(targetType, SUPPORT_TYPES, "targetType");

    final T dto;
    if (null == article) {
      dto = null;
    } else if (SimpleArticleDto.class == targetType) {
      dto = (T) simple(article);
    } else if (SummaryArticleDto.class == targetType) {
      dto = (T) summary(article);
    } else if (DetailArticleDto.class == targetType) {
      dto = (T) detail(article);
    } else {
      throw new IllegalStateException(format("not implemented to %s", targetType));
    }

    if (log.isTraceEnabled()) {
      log.trace("return : {}", dto);
    }
    return dto;
  }
}