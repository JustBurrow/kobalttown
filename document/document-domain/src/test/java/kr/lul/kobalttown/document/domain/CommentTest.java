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
public class CommentTest {
  protected static final Logger log = getLogger(CommentTest.class);

  class Co implements Comment {
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
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final Comment comment = new Co();
    log.info("GIVEN - comment={}", comment);

    // WHEN
    final URI uri = comment.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.Comment/1"));
  }
}
