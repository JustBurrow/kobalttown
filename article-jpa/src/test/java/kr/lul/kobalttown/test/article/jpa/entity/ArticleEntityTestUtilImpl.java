package kr.lul.kobalttown.test.article.jpa.entity;

import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@Service
class ArticleEntityTestUtilImpl implements ArticleEntityTestUtil {
  private static final Logger log = getLogger(ArticleEntityTestUtilImpl.class);

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // kr.lul.kobalttown.test.article.jpa.entity.ArticleEntityTestUtil
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public ArticleEntity random() {
    return null;
  }

  @Override
  public ArticleEntity randomNew() {
    return null;
  }
}