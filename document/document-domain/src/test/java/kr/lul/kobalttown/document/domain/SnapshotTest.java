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
public class SnapshotTest {
  protected static final Logger log = getLogger(SnapshotTest.class);

  private final long ssDocId = 1L;
  private final int ssDocRev = 1;

  class Ss implements Snapshot {
    class SsId implements Id {
      @Override
      public long document() {
        return SnapshotTest.this.ssDocId;
      }

      @Override
      public int version() {
        return SnapshotTest.this.ssDocRev;
      }
    }

    @Override
    public Class<Document> type() {
      return Document.class;
    }

    @Override
    public Id getId() {
      return new SsId();
    }

    @Override
    public int getVision() {
      return SnapshotTest.this.ssDocRev;
    }

    @Override
    public Document getDocument() {
      return null;
    }

    @Override
    public Instant getCreatedAt() {
      return null;
    }
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final Snapshot snapshot = new Ss();
    log.info("GIVEN - snapshot={}", snapshot);

    // WHEN
    final URI uri = snapshot.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Document/1/1"));
  }
}
