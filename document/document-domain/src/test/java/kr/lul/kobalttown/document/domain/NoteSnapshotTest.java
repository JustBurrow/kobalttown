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
public class NoteSnapshotTest {
  protected static final Logger log = getLogger(NoteSnapshotTest.class);

  class Ns implements NoteSnapshot {
    @Override
    public Note getNote() {
      return null;
    }

    @Override
    public String getBody() {
      return null;
    }

    @Override
    public NoteSnapshot.Id getId() {
      return new NoteSnapshot.Id() {
        @Override
        public long document() {
          return 1L;
        }

        @Override
        public int version() {
          return 0;
        }
      };
    }

    @Override
    public int getVersion() {
      return 0;
    }

    @Override
    public Instant getCreatedAt() {
      return null;
    }

    @Override
    public Instant getDeleted() {
      return null;
    }

    @Override
    public void delete(final Instant deletedAt) throws IllegalStateException {

    }
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final NoteSnapshot snapshot = new Ns();
    log.info("GIVEN - snapshot={}", snapshot);

    // WHEN
    final URI uri = snapshot.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Note/1/0"));
  }
}
