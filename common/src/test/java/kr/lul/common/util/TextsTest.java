package kr.lul.common.util;

import org.junit.Test;
import org.slf4j.Logger;

import static kr.lul.common.util.Texts.doubleQuote;
import static kr.lul.common.util.Texts.singleQuote;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-04
 */
public class TextsTest {
  private static final Logger log = getLogger(TextsTest.class);

  @Test
  public void test_singleQuote_with_null() throws Exception {
    // WHEN
    String actual = singleQuote(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_singleQuote_with_empty() throws Exception {
    // WHEN
    String actual = singleQuote("");
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isEqualTo("''");
  }

  @Test
  public void test_singleQuote_with_a() throws Exception {
    // GIVEN
    CharSequence text = "a";
    log.info("GIVEN - text={}", text);

    // WHEN
    String actual = singleQuote(text);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isEqualTo("'a'");
  }

  @Test
  public void test_doubleQuote_with_null() throws Exception {
    // WHEN
    String actual = doubleQuote(null);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isNull();
  }

  @Test
  public void test_doubleQuote_with_empty() throws Exception {
    // WHEN
    String actual = doubleQuote("");
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isEqualTo("\"\"");
  }

  @Test
  public void test_doubleQuote_with_a() throws Exception {
    // GIVEN
    String text = "a";
    log.info("GIVEN - text={}", text);

    // WHEN
    String actual = doubleQuote(text);
    log.info("WHEN - actual={}", actual);

    // THEN
    assertThat(actual)
        .isEqualTo("\"a\"");
  }
}