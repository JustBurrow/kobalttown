package kr.lul.kobalttown.article.jpa.repository;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.jpa.ArticleJpaTestConfiguration;
import kr.lul.kobalttown.article.jpa.entity.ArticleEntity;
import kr.lul.kobalttown.common.util.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import test.kr.lul.kobalttown.article.jpa.ArticleJpaTestUtil;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-01
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArticleJpaTestConfiguration.class)
@DataJpaTest
public class ArticleRepositoryTest {
  private static final Logger log = getLogger(ArticleRepositoryTest.class);

  @Autowired
  private ArticleRepository articleRepository;
  @Autowired
  private ArticleJpaTestUtil articleJpaTestUtil;
  @Autowired
  private TimeProvider timeProvider;

  private Instant before;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleRepository).isNotNull();
    assertThat(this.articleJpaTestUtil).isNotNull();
    assertThat(this.timeProvider).isNotNull();

    this.before = this.timeProvider.now();
    Thread.sleep(1L);
  }

  @Test
  public void test_findAll() throws Exception {
    // When
    List<ArticleEntity> list = this.articleRepository.findAll();
    log.info("WHEN - list={}", list);

    // Then
    assertThat(list)
        .isNotNull()
        .isEmpty();
  }

  @Test
  public void test_save() throws Exception {
    // Given
    final ArticleEntity article = this.articleJpaTestUtil.prePersistArticle();
    final long id = article.getId();
    final Account author = article.getAuthor();
    final String title = article.getTitle();
    final String summary = article.getSummary();
    final String body = article.getBody();
    final Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    ArticleEntity actual = this.articleRepository.save(article);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(article)
        .isNotNull()
        .extracting(Article::getAuthor, Article::getTitle, Article::getSummary, Article::getBody,
            Article::getCreatedAt)
        .containsSequence(author, title, summary, body, createdAt);
    assertThat(article.getId())
        .isGreaterThan(0L);
    assertThat(article.getCreatedAt())
        .isAfter(this.before);
  }

  @Test
  public void test_save_with_null() throws Exception {
    assertThatThrownBy(() -> this.articleRepository.save(null))
        .isNotNull();
  }
}