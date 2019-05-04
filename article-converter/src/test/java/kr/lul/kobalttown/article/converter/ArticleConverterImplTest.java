package kr.lul.kobalttown.article.converter;

import kr.lul.kobalttown.account.converter.AccountConverter;
import kr.lul.kobalttown.article.domain.Article;
import kr.lul.kobalttown.article.dto.DetailArticleDto;
import kr.lul.kobalttown.article.dto.SimpleArticleDto;
import kr.lul.kobalttown.article.dto.SummaryArticleDto;
import kr.lul.kobalttown.common.util.AssertionException;
import kr.lul.kobalttown.common.util.TimeProvider;
import kr.lul.kobalttown.test.article.ArticleServiceTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.configuration.ArticleConverterTestConfiguration;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArticleConverterTestConfiguration.class)
@Transactional
public class ArticleConverterImplTest {
  private static final Logger log = getLogger(ArticleConverterImplTest.class);

  @Autowired
  private ArticleConverter articleConverter;
  @Autowired
  private AccountConverter accountConverter;
  @Autowired
  private TimeProvider timeProvider;
  @Autowired
  private ArticleServiceTestUtil testUtil;

  @Before
  public void setUp() throws Exception {
    assertThat(this.articleConverter).isNotNull();
    assertThat(this.accountConverter).isNotNull();
    assertThat(this.timeProvider).isNotNull();
    assertThat(this.testUtil).isNotNull();
  }

  @Test
  public void test_simple_with_null() throws Exception {
    assertThat(this.articleConverter.simple(null))
        .isNull();
  }

  @Test
  public void test_simple() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    log.info("GIVEN - article={}", article);

    // When
    SimpleArticleDto dto = this.articleConverter.simple(article);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(SimpleArticleDto::getId, SimpleArticleDto::getTitle)
        .containsSequence(id, title);
  }

  @Test
  public void test_summary_with_null() throws Exception {
    assertThat(this.articleConverter.summary(null))
        .isNull();
  }

  @Test
  public void test_summary() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    String summary = article.getSummary();
    Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    SummaryArticleDto dto = this.articleConverter.summary(article);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(SummaryArticleDto::getId, SummaryArticleDto::getTitle, SummaryArticleDto::getSummary,
            SummaryArticleDto::getCreatedAt)
        .containsSequence(id, title, summary,
            this.timeProvider.zonedDateTime(createdAt));
  }

  @Test
  public void test_detail_with_null() throws Exception {
    assertThat(this.articleConverter.detail(null))
        .isNull();
  }

  @Test
  public void test_detail() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    String summary = article.getSummary();
    String body = article.getBody();
    Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    DetailArticleDto dto = this.articleConverter.detail(article);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(DetailArticleDto::getId, DetailArticleDto::getTitle,
            DetailArticleDto::getSummary, DetailArticleDto::getBody,
            DetailArticleDto::getCreatedAt)
        .containsSequence(id, title, summary, body,
            this.timeProvider.zonedDateTime(createdAt));
  }

  @Test
  public void test_convert_with_null_article_and_null_type() throws Exception {
    assertThatThrownBy(() -> this.articleConverter.convert(null, null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("targetType is null.");
  }

  @Test
  public void test_convert_with_null_article_and_simple() throws Exception {
    assertThat(this.articleConverter.convert(null, SimpleArticleDto.class))
        .isNull();
  }

  @Test
  public void test_convert_with_null_article_and_summary() throws Exception {
    assertThat(this.articleConverter.convert(null, SummaryArticleDto.class))
        .isNull();
  }

  @Test
  public void test_convert_with_null_article_and_detail() throws Exception {
    assertThat(this.articleConverter.convert(null, DetailArticleDto.class))
        .isNull();
  }

  @Test
  public void test_convert_with_simple_type() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    log.info("GIVEN - article={}", article);

    // When
    SimpleArticleDto dto = this.articleConverter.convert(article, SimpleArticleDto.class);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(SimpleArticleDto::getId, SimpleArticleDto::getTitle)
        .containsSequence(id, title);
  }

  @Test
  public void test_convert_with_summary_type() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    String summary = article.getSummary();
    Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    SummaryArticleDto dto = this.articleConverter.convert(article, SummaryArticleDto.class);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(SummaryArticleDto::getId, SummaryArticleDto::getTitle, SummaryArticleDto::getSummary,
            SummaryArticleDto::getCreatedAt)
        .containsSequence(id, title, summary,
            this.timeProvider.zonedDateTime(createdAt));
  }

  @Test
  public void test_convert_with_detail_type() throws Exception {
    // Given
    Article article = this.testUtil.createdArticle();
    long id = article.getId();
    String title = article.getTitle();
    String summary = article.getSummary();
    String body = article.getBody();
    Instant createdAt = article.getCreatedAt();
    log.info("GIVEN - article={}", article);

    // When
    DetailArticleDto dto = this.articleConverter.convert(article, DetailArticleDto.class);
    log.info("WHEN - dto={}", dto);

    // Then
    assertThat(dto)
        .isNotNull()
        .extracting(DetailArticleDto::getId,
            DetailArticleDto::getTitle, DetailArticleDto::getSummary, DetailArticleDto::getBody,
            DetailArticleDto::getCreatedAt)
        .containsSequence(id, title, summary, body,
            this.timeProvider.zonedDateTime(createdAt));
  }
}