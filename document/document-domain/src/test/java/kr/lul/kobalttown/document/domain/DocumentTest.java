package kr.lul.kobalttown.document.domain;

import org.junit.Test;
import org.slf4j.Logger;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/29
 */
public class DocumentTest {
  protected static final Logger log = getLogger(DocumentTest.class);

  class D implements Document<D> {
    @Override
    public long getId() {
      return 1L;
    }

    @Override
    public int getVersion() {
      return 0;
    }

    @Override
    public String getKey() {
      return null;
    }

    @Override
    public History<D> getHistory(final int size, final int page) {
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
  public void test_getKey() throws Exception {
    // GIVEN
    final D d = new D();

    // WHEN
    final String key = d.getKey();
    log.info("WHEN - key={}", key);

    // THEN
    assertThat(key)
        .startsWith(D.class.getCanonicalName())
        .endsWith(Long.toString(1L));
  }
}
