package kr.lul.kobalttown.test.article.domain;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static kr.lul.kobalttown.article.domain.Article.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-03
 */
public class ArticleDomainTestUtilTest {
  private static final Logger log = getLogger(ArticleDomainTestUtilTest.class);

  private Random random;

  @Before
  public void setUp() throws Exception {
    this.random = ThreadLocalRandom.current();
  }

  @Test
  public void test_title() throws Exception {
    for (int i = 0; i < 10000; i++) {
      // When
      String title = ArticleDomainTestUtil.title();
      // log.info("WHEN - title={}", title);

      // Then
      assertThat(title)
          .isNotNull()
          .isNotEmpty();
      assertThat(title.length())
          .as("title.length=%d, max=%d", title.length(), TITLE_MAX_LENGTH)
          .isLessThanOrEqualTo(TITLE_MAX_LENGTH);
      validateTitle(title);
    }
  }

  @Test
  public void test_summary() throws Exception {
    for (int i = 0; i < 10000; i++) {
      // When
      String summary = ArticleDomainTestUtil.summary();
      //log.info("WHEN - summary={}", summary);

      // Then
      assertThat(summary)
          .isNotEmpty();
      assertThat(summary.length())
          .isLessThanOrEqualTo(SUMMARY_MAX_LENGTH);
      validateSummary(summary);
    }
  }

  @Test
  public void test_body() throws Exception {
    for (int i = 0; i < 1000; i++) {
      // When
      String body = ArticleDomainTestUtil.body();
      //log.info("WHEN - body={}", body);

      // Then
      assertThat(body)
          .isNotEmpty();
      validateBody(body);
    }
  }
}