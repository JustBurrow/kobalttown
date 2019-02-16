package kr.lul.kobalttown.web.context;

import kr.lul.common.util.AssertionException;
import kr.lul.kobalttown.domain.Papermark;
import kr.lul.kobalttown.domain.Verb;
import kr.lul.kobalttown.domain.VerbPathPapermark;
import kr.lul.kobalttown.web.controller.TestRequestContext;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-01-05
 */
public class VerbPathPapermarkConverterTest {
  private static final Logger log = getLogger(VerbPathPapermarkConverterTest.class);

  private BasicPapermarkConverter converter;

  @Before
  public void setUp() throws Exception {
    this.converter = new BasicPapermarkConverter();

    log.info("SETUP - converter={}", this.converter);
  }

  @Test
  public void test_convert_with_null() throws Exception {
    assertThatThrownBy(() -> this.converter.convert(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("requestContext is null.");
  }

  @Test
  public void test_convert_with_read_and_fixed_path() throws Exception {
    // GIVEN
    Path path = Paths.get("a", "b", "c");
    log.info("GIVEN - path={}", path);
    RequestContext requestContext = new TestRequestContext(Verb.READ, path);
    log.info("GIVEN - requestContext={}", requestContext);

    // WHEN
    Papermark papermark = this.converter.convert(requestContext);
    log.info("WHEN - papermark={}", papermark);

    // THEN
    assertThat(papermark)
        .isInstanceOf(VerbPathPapermark.class);
    assertThat((VerbPathPapermark) papermark)
        .extracting(VerbPathPapermark::getVerb, VerbPathPapermark::getPath)
        .containsSequence(Verb.READ, path);
  }
}