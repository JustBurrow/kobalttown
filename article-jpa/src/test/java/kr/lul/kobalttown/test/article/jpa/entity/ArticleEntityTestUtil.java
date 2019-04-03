package kr.lul.kobalttown.test.article.jpa.entity;

import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;

/**
 * {@link ArticleEntity}관련 테스트 유틸리티.
 *
 * @author justburrow
 * @since 2019-04-01
 */
public interface ArticleEntityTestUtil {
  /**
   * 임의의 JPA 엔티티를 반환한다. 신규 저장 가능한지 여부는 상관하지 않는다.
   *
   * @return 임의의 JPA 엔티티.
   */
  ArticleEntity random();

  /**
   * 새로 저장할 수 있는 JPA 엔티티를 생성한다.
   *
   * @return 임의의 새 JPA 엔티티.
   */
  ArticleEntity randomNew();
}