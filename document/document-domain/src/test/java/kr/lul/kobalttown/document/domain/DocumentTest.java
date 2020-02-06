package kr.lul.kobalttown.document.domain;

import org.junit.Test;
import org.slf4j.Logger;

import java.net.URI;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/29
 */
public class DocumentTest {
  protected static final Logger log = getLogger(DocumentTest.class);

  class Doc implements Document {
    @Override
    public Class<Document> type() {
      return Document.class;
    }

    @Override
    public long getId() {
      return 1L;
    }

    @Override
    public int getVersion() {
      return 0;
    }

    @Override
    public History getHistory(final int size, final int page) {
      return null;
    }

    @Override
    public Instant getCreatedAt() {
      return null;
    }

    @Override
    public Instant getUpdatedAt() {
      return null;
    }
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final Document document = new Doc();
    log.info("GIVEN - document={}", document);

    // WHEN
    final URI uri = document.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Document/1"));
  }
}
