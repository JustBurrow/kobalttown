package kr.lul.kobalttown.article.domain;

import kr.lul.kobalttown.common.util.ValidationException;
import org.junit.Test;
import org.slf4j.Logger;

import static java.util.concurrent.ThreadLocalRandom.current;
import static kr.lul.kobalttown.article.domain.Article.*;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-04-03
 */
public class ArticleTest {
  private static final Logger log = getLogger(ArticleTest.class);

  @Test
  public void test_validateTitle_with_null() throws Exception {
    assertThatThrownBy(() -> validateTitle(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("title is null.");
  }

  @Test
  public void test_validateTitle_with_empty() throws Exception {
    assertThatThrownBy(() -> validateTitle(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("title is empty.");
  }

  @Test
  public void test_validateTitle_with_min_title() throws Exception {
    // Given
    String title = random(1);
    log.info("GIVEN - title={}", title);

    // When & Then
    validateTitle(title);
  }

  @Test
  public void test_validateTitle_with_max_title() throws Exception {
    // Given
    String title = random(TITLE_MAX_LENGTH);
    log.info("GIVEN - title={}", title);

    // When & Then
    validateTitle(title);
  }

  @Test
  public void test_validateTitle_with_max_plus_1_title() throws Exception {
    // Given
    String title = random(1 + TITLE_MAX_LENGTH);
    log.info("GIVEN - title={}", title);

    // When & Then
    assertThatThrownBy(() -> validateTitle(title))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("title is too long");
  }

  @Test
  public void test_validateSummary_with_null() throws Exception {
    assertThatThrownBy(() -> validateSummary(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("summary is null.");
  }

  @Test
  public void test_validateSummary_with_empty() throws Exception {
    assertThatThrownBy(() -> validateSummary(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("summary is empty.");
  }

  @Test
  public void test_validateSummary_with_max_length() throws Exception {
    validateSummary(random(SUMMARY_MAX_LENGTH));
  }

  @Test
  public void test_validateSummary_with_max_plus_1_length() throws Exception {
    assertThatThrownBy(() -> validateSummary(random(1 + SUMMARY_MAX_LENGTH)))
        .isInstanceOf(ValidationException.class)
        .hasMessageStartingWith("summary is too long");
  }

  @Test
  public void test_validateBody_with_null() throws Exception {
    assertThatThrownBy(() -> validateBody(null))
        .isInstanceOf(ValidationException.class)
        .hasMessage("body is null.");
  }

  @Test
  public void test_validateBody_with_empty() throws Exception {
    assertThatThrownBy(() -> validateBody(""))
        .isInstanceOf(ValidationException.class)
        .hasMessage("body is empty.");
  }

  @Test
  public void test_validateBody() throws Exception {
    validateBody(random(1 + current().nextInt(10000)));
  }
}