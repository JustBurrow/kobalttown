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
public class NoteRecommentTest {
  protected static final Logger log = getLogger(NoteRecommentTest.class);

  class Nrc implements NoteRecomment {
    @Override
    public NoteComment getComment() {
      return null;
    }

    @Override
    public Note getNote() {
      return null;
    }

    @Override
    public NoteSnapshot getSnapshot() {
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
    final NoteRecomment recomment = new Nrc();
    log.info("GIVEN - recomment={}", recomment);

    // WHEN
    final URI uri = recomment.uri();
    log.info("WHEN - uri={}", uri);

    // THEN
    assertThat(uri)
        .isNotNull()
        .isEqualTo(new URI("kd://kobalttown/kr.lul.kobalttown.document.domain.NoteComment/1"));
  }
}
