package kr.lul.kobalttown.document.domain;

import kr.lul.common.util.ValidationException;
import kr.lul.kobalttown.account.domain.Account;
import org.junit.Test;
import org.slf4j.Logger;

import java.net.URI;
import java.time.Instant;

import static kr.lul.kobalttown.document.domain.Document.OWNER_VALIDATOR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author justburrow
 * @since 2020/01/29
 */
public class DocumentTest {
  protected static final Logger log = getLogger(DocumentTest.class);

  @Test
  public void test_OWNER_VALIDATOR_with_null() throws Exception {
    assertThatThrownBy(() -> OWNER_VALIDATOR.validate(null))
        .isNotNull()
        .isInstanceOf(ValidationException.class)
        .hasMessage("owner is null.");
  }

  @Test
  public void test_OWNER_VALIDATOR_with_not_persisted() throws Exception {
    assertThatThrownBy(() -> OWNER_VALIDATOR.validate(new Account() {
      @Override
      public long getId() {
        return 0L;  // TEST
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return false;
      }

      @Override
      public void enable(final Instant enableAt) {

      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    }))
        .isInstanceOf(ValidationException.class)
        .hasMessage("owner is not persisted.");
  }

  @Test
  public void test_OWNER_VALIDATOR_with_not_enabled() throws Exception {
    assertThatThrownBy(() -> OWNER_VALIDATOR.validate(new Account() {
      @Override
      public long getId() {
        return 1L;
      }

      @Override
      public String getNickname() {
        return null;
      }

      @Override
      public boolean isEnabled() {
        return false; // TEST
      }

      @Override
      public void enable(final Instant enableAt) {

      }

      @Override
      public Instant getCreatedAt() {
        return null;
      }

      @Override
      public Instant getUpdatedAt() {
        return null;
      }
    }))
        .isInstanceOf(ValidationException.class)
        .hasMessage("owner is not enabled.");
  }

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
    public Account getOwner() {
      return null;
    }

    @Override
    public History history(final int size, final int page) {
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
