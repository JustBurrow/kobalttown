package kr.lul.kobalttown.document.domain;

import org.junit.Test;
import org.slf4j.Logger;

import java.net.URI;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public class NoteTest {
  protected static final Logger log = getLogger(NoteTest.class);

  class N implements Note {
    @Override
    public String getBody() {
      return "test note body.";
    }

    @Override
    public long getId() {
      return 1L;
    }

    @Override
    public int getVersion() {
      return 3;
    }

    @Override
    public NoteHistory getHistory(final int size, final int page) {
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
    final Note note = new N();
    log.info("GIVEN - note={}", note);

    // WHEN
    final URI uri = note.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Note/1"));
  }
}
