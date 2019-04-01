package kr.lul.kobalttown.article.jpa.repository;

import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
}