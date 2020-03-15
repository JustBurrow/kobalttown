package kr.lul.kobalttown.document.domain;

import kr.lul.kobalttown.account.domain.Account;
import org.junit.Test;
import org.slf4j.Logger;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/02/06
 */
public class NoteCommentTest {
  protected static final Logger log = getLogger(NoteCommentTest.class);

  class Nc implements NoteComment {
    @Override
    public Note getNote() {
      return null;
    }

    @Override
    public NoteSnapshot getSnapshot() {
      return null;
    }

    @Override
    public Account getAuthor() {
      return null;
    }

    @Override
    public long getId() {
      return 1L;
    }

    @Override
    public String getBody() {
      return null;
    }
  }

  @Test
  public void test_uri() throws Exception {
    // GIVEN
    final NoteComment comment = new Nc();
    log.info("GIVEN - comment={}", comment);

    // WHEN
    final URI uri = comment.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.NoteComment/1"));
  }
}
