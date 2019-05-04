package kr.lul.kobalttown.article.jpa.entity;

import kr.lul.kobalttown.account.jpa.entity.AccountEntity;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.account.AccountJpaTestUtil;
import kr.lul.kobalttown.test.article.ArticleJpaTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import test.configuration.ArticleJpaTestConfiguration;

import java.time.Instant;

import static kr.lul.kobalttown.test.article.ArticleDomainTestUtil.body;
import static kr.lul.kobalttown.test.article.ArticleDomainTestUtil.title;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-10
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ArticleJpaTestConfiguration.class)
@DataJpaTest
public class ArticleEntityTest {
  private static final Logger log = getLogger(ArticleEntityTest.class);

  @Autowired
  private ArticleJpaTestUtil articleJpaTestUtil;
  @Autowired
  private AccountJpaTestUtil accountJpaTestUtil;
  @Autowired
  private TimeProvider timeProvider;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleJpaTestUtil).isNotNull();
    assertThat(this.accountJpaTestUtil).isNotNull();
    assertThat(this.timeProvider).isNotNull();
  }

  @Test
  public void test_new_with_nulls() throws Exception {
    assertThatThrownBy(() -> new ArticleEntity(null, null, null, null))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("is null.");
  }

  @Test
  public void test_new_with_pre_persist_author() throws Exception {
    // Given
    AccountEntity account = this.accountJpaTestUtil.prePersistAccount();
    log.info("GIVEN - account={}", account);

    // When & Then
    assertThatThrownBy(() -> new ArticleEntity(title(), body(), account, this.timeProvider.now()))
        .isInstanceOf(AssertionException.class)
        .hasMessageContaining("is not positive");
  }

  @Test
  public void test_equals_with_same_pre_persist() throws Exception {
    // Given
    ArticleEntity article = this.articleJpaTestUtil.prePersistArticle();
    log.info("GIVEN - article={}", article);

    // When & Then
    assertThat(article.equals(article))
        .isTrue();
  }

  @Test
  public void test_equals_with_pre_persist() throws Exception {
    // Given
    AccountEntity author = this.accountJpaTestUtil.persistedAccount();
    String title = title();
    String body = body();
    Instant createdAt = this.timeProvider.now();
    log.info("GIVEN - author={}, title={}, body={}, createdAt={}", author, title, body, createdAt);

    // When
    ArticleEntity article1 = new ArticleEntity(title, body, author, createdAt);
    ArticleEntity article2 = new ArticleEntity(title, body, author, createdAt);
    log.info("WHEN - article1={}, article2={}", article1, article2);

    // Then
    assertThat(article1)
        .isNotSameAs(article2)
        .isNotEqualTo(article2)
        .extracting(ArticleEntity::getId, ArticleEntity::getId)
        .containsSequence(0L, article2.getId());
    assertThat(article2)
        .isNotSameAs(article1)
        .isNotEqualTo(article1)
        .extracting(ArticleEntity::getId, ArticleEntity::getId)
        .containsSequence(0L, article1.getId());
  }
}