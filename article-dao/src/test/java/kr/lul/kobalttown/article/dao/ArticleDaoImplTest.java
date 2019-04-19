package kr.lul.kobalttown.article.dao;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.test.article.ArticleDaoTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.ArticleDaoTestConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleDaoTestConfiguration.class)
@Transactional
public class ArticleDaoImplTest {
  private static final Logger log = getLogger(ArticleDaoImplTest.class);

  @Autowired
  private ArticleDao articleDao;
  @Autowired
  private ArticleDaoTestUtil testUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleDao).isNotNull();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.articleDao.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("article is null.");
  }

  @Test
  public void test_create() throws Exception {
    // Given
    final Article article = this.testUtil.prePersistArticle();
    final long id = article.getId();
    final String title = article.getTitle();
    final String summary = article.getSummary();
    final String body = article.getBody();
    final Account creator = article.getCreator();
    final Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    final Article actual = this.articleDao.create(article);
    log.info("WHEN - actual={}", actual);

    // Then
    assertThat(actual)
        .isNotNull()
        .extracting(Article::getTitle, Article::getSummary, Article::getBody,
            Article::getAuthor, Article::getCreator, Article::getCreatedAt)
        .containsSequence(title, summary, body, creator, creator, createdAt);
    assertThat(actual.getId())
        .isGreaterThan(0)
        .isNotEqualTo(id);
  }

  @Test
  public void test_create_with_created() throws Exception {
    // Given
    Article article = this.testUtil.persistedArticle();
    log.info("GIVEN - article={}", article);

    // When & Then
    assertThatThrownBy(() -> this.articleDao.create(article))
        .isInstanceOf(AssertionException.class)
        .hasMessageStartingWith("article.id is positive");
  }
}