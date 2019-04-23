package kr.lul.kobalttown.article.service;

import kr.lul.kobalttown.account.domain.Account;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.service.params.CreateArticleParams;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.test.article.ArticleServiceTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.ArticleServiceTestConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleServiceTestConfiguration.class)
@Transactional
public class ArticleServiceImplTest {
  private static final Logger log = getLogger(ArticleServiceImplTest.class);

  @Autowired
  private ArticleService articleService;
  @Autowired
  private ArticleServiceTestUtil testUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleService).isNotNull();
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.articleService.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("params is null.");
  }

  @Test
  public void test_create() throws Exception {
    // Given
    CreateArticleParams params = this.testUtil.createArticleParams();
    Account creator = params.getCreator();
    String title = params.getTitle();
    String body = params.getBody();
    Instant timestamp = params.getTimestamp();
    log.info("GIVEN - params={}", params);

    // When
    Article article = this.articleService.create(params);
    log.info("WHEN - article={}", article);

    // Then
    assertThat(article)
        .isNotNull()
        .extracting(Article::getTitle, Article::getBody, Article::getCreator, Article::getAuthor, Article::getCreatedAt)
        .containsSequence(title, body, creator, creator, timestamp);
    assertThat(article.getId())
        .isGreaterThan(0);
    assertThat(article.getBody())
        .startsWith(article.getSummary());
  }
}