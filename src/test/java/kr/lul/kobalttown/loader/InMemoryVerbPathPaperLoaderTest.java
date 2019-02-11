package kr.lul.kobalttown.loader;

import kr.lul.common.util.AssertionException;
import kr.lul.kobalttown.domain.DummyPaper;
import kr.lul.kobalttown.domain.Paper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2019-02-10
 */
public class InMemoryVerbPathPaperLoaderTest {
  private static final Logger log = getLogger(InMemoryVerbPathPaperLoaderTest.class);

  private InMemoryVerbPathPaperLoader loader;

  private Random random = new Random();

  @Before
  public void setUp() throws Exception {
    this.loader = new InMemoryVerbPathPaperLoader();

    log.info(format("SETUP - loader=%s", this.loader));
  }

  @Test
  public void test_load_with_null() throws Exception {
    assertThatThrownBy(() -> this.loader.load(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("papermark is null.");
  }

  @Test
  public void test_create_with_null() throws Exception {
    assertThatThrownBy(() -> this.loader.create(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("path is null.");
  }

  @Test
  public void test_read_with_null() throws Exception {
    assertThatThrownBy(() -> this.loader.read(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("path is null.");
  }

  @Test
  public void test_read_not_exists() throws Exception {
    // Given
    Path path = Paths.get("/", randomAlphanumeric(0, 10));
    log.info(format("GIVEN - path=%s", path));

    // When
    Paper paper = this.loader.read(path);
    log.info(format("WHEN - paper=%s", paper));

    // Then
    assertThat(paper)
        .isNull();
  }

  @Test
  public void test_read_exists() throws Exception {
    // Given
    final Path path = Paths.get("/", randomAlphanumeric(0, 10));
    this.loader.addPaper(new DummyPaper(path));
    for (int i = 3 + this.random.nextInt(10); i >= 0; i--) {
      this.loader.addPaper(new DummyPaper(Paths.get("/test", randomAlphanumeric(1, 100))));
    }
    log.info(format("GIVEN - loader=%s", this.loader));

    // When
    Paper paper = this.loader.read(path);
    log.info(format("WHEN - paper=%s", paper));

    // Then
    assertThat(paper)
        .isNotNull()
        .extracting(Paper::getPath, Paper::getTheme)
        .containsSequence(path, Paper.DEFAULT_THEME);
  }

  @Test
  public void test_update_with_null() throws Exception {
    assertThatThrownBy(() -> this.loader.update(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("path is null.");
  }

  @Test
  public void test_delete_with_null() throws Exception {
    assertThatThrownBy(() -> this.loader.delete(null))
        .isInstanceOf(AssertionException.class)
        .hasMessage("path is null.");
  }
}