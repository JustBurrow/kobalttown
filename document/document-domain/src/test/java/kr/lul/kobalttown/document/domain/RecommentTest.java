package kr.lul.kobalttown.document.domain;

import org.junit.Test;
import org.slf4j.Logger;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public class RecommentTest {
  protected static final Logger log = getLogger(RecommentTest.class);

  class Reco implements Recomment {
    @Override
    public Comment getComment() {
      return new Comment() {
        @Override
        public Class<Comment> type() {
          return Comment.class;
        }

        @Override
        public long getId() {
          return 1L;
        }

        @Override
        public Document getDocument() {
          return null;
        }

        @Override
        public Snapshot getSnapshot() {
          return null;
        }

        @Override
        public String getBody() {
          return null;
        }
      };
    }

    @Override
    public Class<Comment> type() {
      return Comment.class;
    }

    @Override
    public long getId() {
      return 2L;
    }

    @Override
    public Document getDocument() {
      return null;
    }

    @Override
    public Snapshot getSnapshot() {
      return null;
    }

    @Override
    public String getBody() {
      return null;
    }
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final Recomment recomment = new Reco();
    log.info("GIVEN - recomment={}", recomment);

    // WHEN
    final URI uri = recomment.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Comment/2"));
  }
}
